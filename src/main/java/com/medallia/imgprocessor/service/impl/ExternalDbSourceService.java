package com.medallia.imgprocessor.service.impl;

import com.medallia.imgprocessor.exception.NoDataSourceFoundException;
import com.medallia.imgprocessor.model.DomainDbDTO;
import com.medallia.imgprocessor.entity.ExternalDataSource;
import com.medallia.imgprocessor.model.ExternalDataSourceDTO;
import com.medallia.imgprocessor.repository.ExternalDataSourceRepository;
import com.medallia.imgprocessor.service.IExternalDbSourceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: dheeraj.suthar
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalDbSourceService implements IExternalDbSourceService {

	/**
	 * http://localhost:8080/getDatabases
	 * http://localhost:8080/getDBForDomain/<domain>
	 */
	private final RestTemplate restTemplate;
	private final ExternalDataSourceRepository externalDataSourceRepository;

	@Value("${app.external.datasource.enpoint}")
	private String externalEndpoint;

	@Override
	public List<ExternalDataSourceDTO> getDatabasesFromExternal() {
		ResponseEntity<ExternalDataSourceDTO[]> responseEntity = restTemplate
				.getForEntity(externalEndpoint + "/getDatabases", ExternalDataSourceDTO[].class);
		ExternalDataSourceDTO[] dtoArray = responseEntity.getBody();
		return Arrays.stream(dtoArray).collect(Collectors.toList());
	}

	@Override
	public DomainDbDTO getDatabaseForDomain(@NonNull final String domain) {
		return restTemplate.getForObject(externalEndpoint + "/getDBForDomain/" + domain, DomainDbDTO.class);
	}

	@Override
	@Transactional
	public void saveLiveDatabase(List<ExternalDataSourceDTO> dataSources) {
		if (!CollectionUtils.isEmpty(dataSources)) {
			dataSources.forEach(source -> {
				ExternalDataSource externalDataSource;
				Optional<ExternalDataSource> existingOptional = externalDataSourceRepository.findById(source.getId());
				externalDataSource = existingOptional.orElseGet(ExternalDataSource::new);
				externalDataSource.setId(source.getId());
				externalDataSource.setHost(source.getHost());
				externalDataSource.setDbSchema(source.getSchema());
				externalDataSource.setUserName(source.getUser());
				externalDataSource.setPassword(source.getPassword());
				externalDataSource.setLive(true);

				externalDataSourceRepository.save(externalDataSource);
			});
		}
	}

	@Override
	@Transactional
	public void refreshAvailableDatabases(List<Long> idList) {
		externalDataSourceRepository.refreshDatabasesAvailability(idList);
	}

	@Override
	public ExternalDataSourceDTO getAvailabeleDBByID(Long dbId) throws NoDataSourceFoundException {
		Optional<ExternalDataSource> externalDataSource = externalDataSourceRepository.findAvailableDBById(dbId);
		if (externalDataSource.isEmpty()) {
			throw new NoDataSourceFoundException("No external data source found for db id:" + dbId);
		}
		return new ExternalDataSourceDTO(externalDataSource.get());
	}
}
