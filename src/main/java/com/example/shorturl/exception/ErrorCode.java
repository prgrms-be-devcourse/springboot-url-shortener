package com.example.shorturl.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 URL이 존재하지 않습니다"),
    ALGORITHM_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원하지 않는 인코딩 알고리즘 입니다."),
    ENCODING_FAILED(HttpStatus.BAD_REQUEST, "인코딩에 실패했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorCode defaultError() {
        return INTERNAL_SERVER_ERROR;
    }
}
