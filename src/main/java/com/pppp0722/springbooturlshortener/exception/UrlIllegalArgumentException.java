package com.pppp0722.springbooturlshortener.exception;

public class UrlIllegalArgumentException extends RuntimeException {

    public UrlIllegalArgumentException() {
    }

    public UrlIllegalArgumentException(String message) {
        super(message);
    }
}
