package com.medallia.imgprocessor.service;

import com.medallia.imgprocessor.model.ImageDomainDTO;
import com.medallia.imgprocessor.model.ImagePathDTO;

import java.util.List;

/**
 * @author: dheeraj.suthar
 */
public interface IEventProcessorService {

	void processImageData(ImageDomainDTO data);

	void storeImageData(ImageDomainDTO imageDomainDTO);
}
