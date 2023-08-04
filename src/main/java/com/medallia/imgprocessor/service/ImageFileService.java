package com.medallia.imgprocessor.service;

import com.medallia.imgprocessor.model.ImagePathDTO;
import com.medallia.imgprocessor.model.PaginatedResponse;

/**
 * @author: dheeraj.suthar
 */
public interface ImageFileService {

	PaginatedResponse<ImagePathDTO> getImageFilePaginatedResult(String domain, int page, int size);
}
