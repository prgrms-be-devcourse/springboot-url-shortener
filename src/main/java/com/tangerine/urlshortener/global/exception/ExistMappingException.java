package com.tangerine.urlshortener.global.exception;

public class ExistMappingException extends RuntimeException {

    public ExistMappingException(String message) {
        super(message);
    }
}
