package com.medallia.imgprocessor.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: dheeraj.suthar
 */
@Data
public class DomainDbDTO implements Serializable {

	private static final long serialVersionUID = -877631459408791885L;

	private Long dbId;
	private String domain;
}
