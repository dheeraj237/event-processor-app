package com.medallia.imgprocessor.service.impl;

import com.medallia.imgprocessor.configuration.DynamicDataSourceConfig;
import com.medallia.imgprocessor.consumer.ImageDataStore;
import com.medallia.imgprocessor.entity.ImageDomain;
import com.medallia.imgprocessor.model.DomainDbDTO;
import com.medallia.imgprocessor.model.ExternalDataSourceDTO;
import com.medallia.imgprocessor.model.ImageDomainDTO;
import com.medallia.imgprocessor.service.IExternalDbSourceService;
import com.medallia.imgprocessor.service.IEventProcessorService;
import com.medallia.imgprocessor.service.ImageDomainService;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author: dheeraj.suthar
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EventProcessorService implements IEventProcessorService {

	private final JmsTemplate jmsTemplate;
	private final Queue queue;
	private final IExternalDbSourceService externalDbSourceService;
	private final ImageDomainService imageDomainService;
	private final ImageDataStore imageDataStore;
	private final DynamicDataSourceConfig dynamicDataSourceConfig;

	@Autowired
	@Qualifier("altDataSourceProperties")
	private DataSourceProperties alternateDataSource;

	@Override
	public void processImageData(ImageDomainDTO data) {
		try {
			// publish to broker
			jmsTemplate.convertAndSend(queue, data);
		} catch (Exception e) {
			log.error("Exception occurred while producing data to queue.. {}", e, e);
			imageDataStore.storeData(data);
		}
	}

	@Override
	public void storeImageData(ImageDomainDTO imageDomainDTO) {

		try {
			// store the image data to dynamic db
			DomainDbDTO dto = externalDbSourceService.getDatabaseForDomain(imageDomainDTO.getDomain());
			if (dto != null && dto.getDbId() != null) {
				log.info("DB Id: {} found for domain: {}", dto.getDbId(), imageDomainDTO.getDomain());
				// store data into dynamic db source with initiazing with credentials from db source local memory.
				ExternalDataSourceDTO externalDataSourceDTO = externalDbSourceService.getAvailabeleDBByID(dto.getDbId());

				ExternalDataSourceDTO mockSource = addExternalDataSource(externalDataSourceDTO);
				dynamicDataSourceConfig.setCurrentDataSource(mockSource);

				Optional<ImageDomain> optionalImageDomain = imageDomainService.getImageDomainByName(imageDomainDTO.getDomain());
				ImageDomain newImageDomain = imageDomainDTO.buildImageDomainFromDTO();
				ImageDomain imageDomainEntity = optionalImageDomain.orElse(newImageDomain);
				imageDomainEntity.setUpdated(LocalDateTime.now());
				imageDomainEntity.getImages().addAll(newImageDomain.getImages()); // update the set of image file path
				// TODO: remove pre existing images and replace new images only
				imageDomainService.persistImageData(imageDomainEntity);
				dynamicDataSourceConfig.clearDataSource();
			} else {
				throw new RuntimeException("No live data source found for the image data..");
			}

		} catch (Exception e) {
			log.error("Exception occurred while producing data to queue.. {}", e, e);
			log.info("[Retry] Proceeding to storing the data into queue again...");
			imageDataStore.storeData(imageDomainDTO);
		}
	}

	private ExternalDataSourceDTO addExternalDataSource(ExternalDataSourceDTO externalDataSourceDTO) throws SQLException {

		// HACK: Using only mock / alternate data source for now
		ExternalDataSourceDTO mockSource = new ExternalDataSourceDTO();
		mockSource.setDriverClass(alternateDataSource.getDriverClassName());
		mockSource.setHost(alternateDataSource.getUrl());
		mockSource.setUser(alternateDataSource.getUsername());
		mockSource.setPassword(alternateDataSource.getPassword());
		return mockSource;
	}

}
