package com.prgrms.urlshortener.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ApplicationException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
