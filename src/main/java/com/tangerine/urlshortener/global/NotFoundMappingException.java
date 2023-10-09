package com.tangerine.urlshortener.global;

public class NotFoundMappingException extends RuntimeException {

    public NotFoundMappingException(String message) {
        super(message);
    }
}
