package com.seungwon.springbooturlshortener.exception;

public class ExcessiveRequestException extends RuntimeException {

	public ExcessiveRequestException() {
	}

	public ExcessiveRequestException(String message) {
		super(message);
	}
}
