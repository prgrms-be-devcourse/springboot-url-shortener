package com.programmers.springbooturlshortener.global.domain.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public abstract class UrlApplicationException extends RuntimeException {

	private final Map<String, String> validation = new HashMap<>();

	protected UrlApplicationException(String message) {
		super(message);
	}

	protected UrlApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public abstract int getStatusCode();

	public void addValidation(String fieldName, String message) {
		validation.put(fieldName, message);
	}
}
