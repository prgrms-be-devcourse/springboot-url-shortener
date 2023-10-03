package com.programmers.urlshortener.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private HttpStatus status;
    private String message;

    protected BusinessException(ExceptionRule exceptionRule) {
        super(exceptionRule.getMessage());
        this.status = exceptionRule.getStatus();
        this.message = exceptionRule.getMessage();
    }
}
