package com.programmers.springbooturlshortener.global.presentation.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponse {

	private final String code;
	private final String message;
	private final Map<String, String> validation;

	@Builder
	public ExceptionResponse(String code, String message, Map<String, String> validation) {
		this.code = code;
		this.message = message;
		this.validation = validation != null ? validation : new HashMap<>();
	}

	public void addValidation(String fieldName, String errorMessage) {
		this.validation.put(fieldName, errorMessage);
	}
}
