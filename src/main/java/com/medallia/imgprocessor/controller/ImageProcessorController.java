package com.medallia.imgprocessor.controller;

import com.medallia.imgprocessor.consumer.ImageDataStore;
import com.medallia.imgprocessor.model.*;
import com.medallia.imgprocessor.service.IExternalDbSourceService;
import com.medallia.imgprocessor.service.IEventProcessorService;
import com.medallia.imgprocessor.service.ImageDomainService;
import com.medallia.imgprocessor.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: dheeraj.suthar
 */
@RestController
@RequestMapping(ImageProcessorController.BASE_URL)
@Slf4j
@RequiredArgsConstructor
public class ImageProcessorController extends AbstractBaseController {

	public static final String BASE_URL = "/api";
	public static final String PROCESS = "/process";
	public static final String GET_ALL_EXTERNAL_DB = "/all-external-db";
	public static final String GET_DB_FOR_DOMAIN_DOMAIN = "/db/{domain}";
	public static final String PENDING_IMAGE_DATA = "/pending-images";
	public static final String DOMAIN = "domain";
	public static final String IMAGE_PATHS = "/image-paths";

	private final IEventProcessorService imageProcessorService;
	private final IExternalDbSourceService externalDbSourceService;
	private final ImageFileService imageFileService;
	private final ImageDataStore imageDataStore;

	@PostMapping(PROCESS)
	public ResponseEntity<ResultObject<String>> processImageData(@RequestBody List<ImageDomainDTO> imageDomainDTOList) {
		try {
			for (ImageDomainDTO imageDomainDTO : imageDomainDTOList) {
				// Save the image data to the database
				imageProcessorService.processImageData(imageDomainDTO);
			}
			return getSuccessResponse("Image data processed successfully..");
		} catch (DataAccessException e) {
			return getFailedResponse("Application error. Retry later.");
		}
	}

	@GetMapping(GET_ALL_EXTERNAL_DB)
	public ResponseEntity<ResultObject<List<ExternalDataSourceDTO>>> getExternalDataBases() {
		return getSuccessResponse(externalDbSourceService.getDatabasesFromExternal());
	}


	@GetMapping(GET_DB_FOR_DOMAIN_DOMAIN)
	public ResponseEntity<ResultObject<DomainDbDTO>> getExternalDataBases(@PathVariable(DOMAIN) String domain) {
		return getSuccessResponse(externalDbSourceService.getDatabaseForDomain(domain));
	}

	@GetMapping(PENDING_IMAGE_DATA)
	public ResponseEntity<ResultObject<List<ImageDomainDTO>>> getPendingImageData() {
		return getSuccessResponseForWarning(imageDataStore.getQueueData(), new String[]{"Pending items will be reprocessed in sometime."});
	}

	@GetMapping(IMAGE_PATHS)
	public ResponseEntity<ResultObject<PaginatedResponse<ImagePathDTO>>> getImagesPaths(
			@RequestParam(required = false) String domain,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return getSuccessResponse(imageFileService.getImageFilePaginatedResult(domain, page, size));
	}


}
