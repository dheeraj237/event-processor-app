package com.medallia.imgprocessor.exception;

/**
 * @author: dheeraj.suthar
 */
public class NoDataSourceFoundException extends RuntimeException {
	public NoDataSourceFoundException() {
		super();
	}
	public NoDataSourceFoundException(String message) {
		super(message);
	}
}
