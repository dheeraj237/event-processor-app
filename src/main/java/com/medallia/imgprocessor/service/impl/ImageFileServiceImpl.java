package com.medallia.imgprocessor.service.impl;

import com.medallia.imgprocessor.entity.ImageFile;
import com.medallia.imgprocessor.model.ImagePathDTO;
import com.medallia.imgprocessor.model.PaginatedResponse;
import com.medallia.imgprocessor.repository.ImageFileRepository;
import com.medallia.imgprocessor.service.ImageFileService;
import com.medallia.imgprocessor.utils.ImageFileDataExtractor;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author: dheeraj.suthar
 */
@Service
@RequiredArgsConstructor
public class ImageFileServiceImpl implements ImageFileService {

	private final ImageFileRepository imageFileRepository;


	@Override
	@Transactional(readOnly = true)
	public PaginatedResponse<ImagePathDTO> getImageFilePaginatedResult(String domain, int page, int size) {
		Page<Object[]> pages = null;
		List<ImagePathDTO> dtos = new ArrayList<>();
		PaginatedResponse<ImagePathDTO> paginatedResponse = new PaginatedResponse<>();
		paginatedResponse.setData(dtos);
		Pageable pageable = PageRequest.of(page-1, size, Sort.by("created").descending());
		if (StringUtils.isEmpty(domain)) {
			pages = imageFileRepository.getAllImageFile(pageable);
		} else {
			domain = domain.trim();
			pages = imageFileRepository.getImageFileByDomain(domain, pageable);
		}
		getPaginatedData(pages, paginatedResponse, ImageFileDataExtractor::extract);
		return paginatedResponse;
	}

	private void getPaginatedData(Page<Object[]> pages,
								  PaginatedResponse<ImagePathDTO> paginatedResponse,
								  Function<List<Object[]>, List<ImagePathDTO>> func) {
		if (pages != null && pages.hasContent()) {
			List<ImagePathDTO> imagePathDTOS = func.apply(pages.getContent());
			paginatedResponse.setData(imagePathDTOS);
			paginatedResponse.setCurrentPage(pages.getNumber());
			paginatedResponse.setTotalPages(pages.getTotalPages());
			paginatedResponse.setTotalItems(pages.getTotalElements());
		}
	}
}
