package com.prgrmrs.urlshortener.global;

import com.prgrmrs.urlshortener.exception.ErrorCode;

public record ErrorResponse(String errorCode, String errorMessage) {

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }
}
