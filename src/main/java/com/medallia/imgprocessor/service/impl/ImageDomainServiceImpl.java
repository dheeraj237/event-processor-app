package com.medallia.imgprocessor.service.impl;

import com.medallia.imgprocessor.entity.ImageDomain;
import com.medallia.imgprocessor.entity.ImageFile;
import com.medallia.imgprocessor.model.ImagePathDTO;
import com.medallia.imgprocessor.model.PaginatedResponse;
import com.medallia.imgprocessor.repository.ImageDomainRepository;
import com.medallia.imgprocessor.service.ImageDomainService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: dheeraj.suthar
 */
@Service
@RequiredArgsConstructor
public class ImageDomainServiceImpl implements ImageDomainService {

	private final ImageDomainRepository imageDomainRepository;

	@Transactional
	@Override
	public void saveDataInNewDatabase(ImageDomain data) {
		// Save data to the new database
		imageDomainRepository.save(data);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<ImageDomain> getImageDomainByName(String domain) {
		return imageDomainRepository.findByDomain(domain);
	}

	@Transactional
	@Override
	public void persistImageData(ImageDomain imageDomainEntity) {
		imageDomainRepository.save(imageDomainEntity);
	}


}