package com.urlshortener.url.exception;

public class MaxRequestException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "URL 생성 횟수를 초과하였습니다.";

    public MaxRequestException() {
        this(DEFAULT_MESSAGE);
    }

    public MaxRequestException(String message) {
        super(message);
    }
}
