package com.medallia.imgprocessor.utils;

import com.medallia.imgprocessor.entity.ImageFile;
import com.medallia.imgprocessor.model.ImagePathDTO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: dheeraj.suthar
 */
@Slf4j
public class ImageFileDataExtractor {

	private static final Predicate<Object[]> IS_LONG_ENOUGH = list -> list.length >= 4;


	private static final Function<List<Object>, Long> ID_EXT = record -> (Long) record.get(0);
	private static final Function<List<Object>, String> IMAGE_PATH_EXT = record -> (String) record.get(1);
	private static final Function<List<Object>, Timestamp> CREATED_EXT = record -> (Timestamp) record.get(2);;
	private static final Function<List<Object>, String> DOMAIN_EXT = record -> (String) record.get(3);

	private ImageFileDataExtractor() {}


	public static List<ImagePathDTO> extract(List<Object[]> data) {
		return data.stream()
				.filter(IS_LONG_ENOUGH)
				.map(row -> {
					log.info("Attempting to extract data for for image file domain data: {}", row[0]);
					return row;
				})
				.map(ImageFileDataExtractor::doExtract)
				.collect(Collectors.toList());
	}


	private static ImagePathDTO doExtract(Object[] arr) {
		List<Object> data = new ArrayList<>(Arrays.asList(arr));

		return ImagePathDTO.builder()
				.id(ID_EXT.apply(data))
				.imagePath(IMAGE_PATH_EXT.apply(data))
				.created(CREATED_EXT.apply(data).toLocalDateTime())
				.domain(DOMAIN_EXT.apply(data))
				.build();

	}
}
