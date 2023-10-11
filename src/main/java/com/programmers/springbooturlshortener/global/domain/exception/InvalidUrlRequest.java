package com.programmers.springbooturlshortener.global.domain.exception;

import lombok.Getter;

@Getter
public class InvalidUrlRequest extends UrlApplicationException {

	private static final String MESSAGE = "유효하지 않은 URL 입니다.";

	public InvalidUrlRequest() {
		super(MESSAGE);
	}

	public InvalidUrlRequest(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}
}
