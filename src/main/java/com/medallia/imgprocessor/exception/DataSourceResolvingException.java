package com.medallia.imgprocessor.exception;

/**
 * @author: dheeraj.suthar
 */
public class DataSourceResolvingException extends RuntimeException {

	public DataSourceResolvingException() {
	}

	public DataSourceResolvingException(String message) {
		super(message);
	}

	public DataSourceResolvingException(Throwable throwable, String message) {
		super(message, throwable);
	}


}
