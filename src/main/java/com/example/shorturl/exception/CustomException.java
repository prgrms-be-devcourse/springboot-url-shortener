package com.example.shorturl.exception;

import static com.example.shorturl.exception.ErrorCode.defaultError;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private ErrorCode errorCode = defaultError();

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
