package com.programmers.urlshortener.exception;

public record ErrorResponse(String message) {
    public static ErrorResponse from(String message) {
        return new ErrorResponse(message);
    }
}
