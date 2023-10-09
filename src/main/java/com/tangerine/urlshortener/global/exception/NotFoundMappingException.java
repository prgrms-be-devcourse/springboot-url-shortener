package com.tangerine.urlshortener.global.exception;

public class NotFoundMappingException extends RuntimeException {

    public NotFoundMappingException(String message) {
        super(message);
    }
}
