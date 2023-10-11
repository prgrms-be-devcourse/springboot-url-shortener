package com.prgrmrs.urlshortener.exception;

public class UrlShortenerException extends RuntimeException {

    private final ErrorCode errorCode;

    public UrlShortenerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
