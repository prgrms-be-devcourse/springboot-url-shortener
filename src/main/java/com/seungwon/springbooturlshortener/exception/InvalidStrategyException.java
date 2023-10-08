package com.seungwon.springbooturlshortener.exception;

public class InvalidStrategyException extends RuntimeException {
	public InvalidStrategyException() {
	}

	public InvalidStrategyException(String userInput) {
		super(userInput);
	}
}
