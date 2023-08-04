package com.medallia.imgprocessor.controller;

import com.medallia.imgprocessor.model.ResultObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * @author: dheeraj.suthar
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	// all error handler advice
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAppException(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(new ResultObject<Object>(null, ResultObject.Type.ERROR, List.of(ex.getMessage())), HttpStatus.NOT_FOUND);
	}
	//other exception handlers below

}