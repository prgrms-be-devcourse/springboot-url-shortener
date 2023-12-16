package com.prgrms.shorturl.exception;

public class NoSuchOriginalUrlException extends RuntimeException {
    public NoSuchOriginalUrlException(String message) {
        super(message);
    }
}
