package com.pppp0722.springbooturlshortener.exception;

public class IllegalUrlArgumentException extends RuntimeException {

    public IllegalUrlArgumentException() {
    }

    public IllegalUrlArgumentException(String message) {
        super(message);
    }
}
