package com.example.urlmanagement.exception;

import lombok.Getter;

@Getter
public class UrlException extends RuntimeException {

    private final UrlErrorCode errorCode;

    public UrlException(UrlErrorCode urlErrorCode, String wrongInput) {
        this.errorCode = urlErrorCode;
        errorCode.updateErrorMessage(wrongInput);
    }
}
