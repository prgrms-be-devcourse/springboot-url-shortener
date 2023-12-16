package com.springboot.springbooturlshortner.exception;

import lombok.Getter;

@Getter
public class UrlException extends RuntimeException {

    private final String message;

    public UrlException(UrlExceptionCode urlExceptionCode) {
        this.message = urlExceptionCode.getMessage();
    }
}
