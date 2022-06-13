package com.spring.shorturl.global.exception;

public class NotFoundEntityByIdException extends RuntimeException {
    public NotFoundEntityByIdException(String message) {
        super(message);
    }
}
