package com.tangerine.urlshortener.global;

public class ExistMappingException extends RuntimeException {

    public ExistMappingException(String message) {
        super(message);
    }
}
