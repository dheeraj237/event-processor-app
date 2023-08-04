package com.medallia.imgprocessor.service;

import com.medallia.imgprocessor.entity.ImageDomain;
import com.medallia.imgprocessor.model.ImagePathDTO;
import com.medallia.imgprocessor.model.PaginatedResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author: dheeraj.suthar
 */
public interface ImageDomainService {

	void saveDataInNewDatabase(ImageDomain data);

	Optional<ImageDomain> getImageDomainByName(String domain);

	void persistImageData(ImageDomain imageDomainEntity);
}
