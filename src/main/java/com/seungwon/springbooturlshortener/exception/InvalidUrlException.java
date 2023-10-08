package com.seungwon.springbooturlshortener.exception;

public class InvalidUrlException extends RuntimeException {
	public InvalidUrlException() {
		super();
	}

	public InvalidUrlException(String message) {
		super(message);
	}
}
