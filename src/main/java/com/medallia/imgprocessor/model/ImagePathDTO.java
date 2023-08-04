package com.medallia.imgprocessor.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: dheeraj.suthar
 */
@Data
@Builder
public class ImagePathDTO implements Serializable {

	private static final long serialVersionUID = -1714778535862146706L;

	private Long id;
	private String domain;
	private String imagePath;
	private LocalDateTime created;
}
