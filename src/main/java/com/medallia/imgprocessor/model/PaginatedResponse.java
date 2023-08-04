package com.medallia.imgprocessor.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: dheeraj.suthar
 */
@Data
public class PaginatedResponse<T> implements Serializable {


	private static final long serialVersionUID = -6986630178413849876L;

	List<T> data;
	private Integer currentPage = 0;
	private Long totalItems = 0L;
	private Integer totalPages = 0;
}
