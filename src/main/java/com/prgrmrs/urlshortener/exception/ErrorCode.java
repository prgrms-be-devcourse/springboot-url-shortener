package com.prgrmrs.urlshortener.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.StringJoiner;
import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "050", "Internal Server Error (서버 내부 에러입니다.)"),

    // urlMapping
    BLANK_ORIGINAL_URL(BAD_REQUEST, "040", "Validation failed (유효성 검증에 실패하였습니다.)"),
    URL_NOT_FOUND(BAD_REQUEST, "040", "Validation failed (유효성 검증에 실패하였습니다.)");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorCode.class.getSimpleName() + "[", "]")
                .add("httpStatus=" + httpStatus)
                .add("code='" + code + "'")
                .add("message='" + message + "'")
                .toString();
    }
}
