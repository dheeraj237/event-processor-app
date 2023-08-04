package com.medallia.imgprocessor.controller;

import com.medallia.imgprocessor.model.ResultObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

/**
 * @author: dheeraj.suthar
 */
public abstract class AbstractBaseController {


	public static final String SUCCESSFUL_RESPONSE = "Successful Response";
	public static final String WARING_MESSAGES = "Waring messages";
	public static final String THERE_IS_SOME_ISSUE_AT_SERVER = "There is some issue at server.";

	/**
	 * Return the success response entity object.
	 */
	public <T> ResponseEntity<ResultObject<T>> getSuccessResponse(T data, String[] messages) {
		ResultObject<T> resultObject = new ResultObject<>(data, ResultObject.Type.INFO, Arrays.asList(messages));
		return new ResponseEntity<>(resultObject, HttpStatus.OK);
	}

	public <T> ResponseEntity<ResultObject<T>> getSuccessResponse(T data) {
		return getSuccessResponse(data, new String[]{SUCCESSFUL_RESPONSE});
	}


	/**
	 * Return the success response entity object with warning type.
	 */
	public <T> ResponseEntity<ResultObject<T>> getSuccessResponseForWarning(T data, String[] messages) {
		ResultObject<T> resultObject = new ResultObject<>(data, ResultObject.Type.WARN, Arrays.asList(messages));
		return new ResponseEntity<>(resultObject, HttpStatus.OK);
	}

	public <T> ResponseEntity<ResultObject<T>> getSuccessResponseForWarning(T data) {
		return getSuccessResponseForWarning(data, new String[]{WARING_MESSAGES});
	}

	/**
	 * Return the success response entity object with warning type.
	 */
	public <T> ResponseEntity<ResultObject<T>> getFailedResponse(T data, String[] messages) {
		ResultObject<T> resultObject = new ResultObject<>(data, ResultObject.Type.ERROR, Arrays.asList(messages));
		return new ResponseEntity<>(resultObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public <T> ResponseEntity<ResultObject<T>> getFailedResponse(T data) {
		return getFailedResponse(data, new String[]{THERE_IS_SOME_ISSUE_AT_SERVER});
	}
}
