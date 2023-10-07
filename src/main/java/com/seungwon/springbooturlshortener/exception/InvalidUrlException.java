package com.seungwon.springbooturlshortener.exception;

public class InvalidUrlException extends RuntimeException {
	public InvalidUrlException() {
		super("유효하지 않은 url 입니다");
	}

	public InvalidUrlException(String message) {
		super(message);
	}
}
