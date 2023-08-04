package com.medallia.imgprocessor.controller;

import com.medallia.imgprocessor.model.ImagePathDTO;
import com.medallia.imgprocessor.model.PaginatedResponse;
import com.medallia.imgprocessor.service.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * controller views to see the paginated data.
 * @author: dheeraj.suthar
 */
@Controller
public class ImageDataController {

	@Autowired
	private ImageFileService imageFileService;

	@GetMapping("/image-data")
	public String getImagesPaths(
			Model model,
			@RequestParam(name = "domain", required = false) String domain,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size) {

		PaginatedResponse<ImagePathDTO> paginatedResponse = imageFileService.getImageFilePaginatedResult(domain, page, size);
		model.addAttribute("data", paginatedResponse.getData());
		model.addAttribute("currentPage", paginatedResponse.getCurrentPage());
		model.addAttribute("totalItems", paginatedResponse.getTotalItems());
		model.addAttribute("totalPages", paginatedResponse.getTotalPages());
		return "index";
	}
}
