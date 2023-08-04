package com.medallia.imgprocessor.model;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * @author: dheeraj.suthar
 */
@Value
public class ResultObject<T> implements Serializable {

	private static final long serialVersionUID = -1759313206639363034L;

	public enum Type {
		INFO, WARN, ERROR
	}

	private T data;

	private Type type;

	// contains all validation business message.
	private List<String> messages;

	public ResultObject(T data, Type type, List<String> messages) {
		this.data = data;
		this.type = type;
		this.messages = messages;
	}
}
