package com.springboot.springbooturlshortner.exception;

import lombok.Getter;

@Getter
public enum UrlExceptionCode {

    NOT_FOUND("존재하지 않는 URL입니다."),
    INVALID_ORIGIN_URL("올바르지 않은 URL 형식입니다.");

    private final String message;

    UrlExceptionCode(String message) {
        this.message = message;
    }

}
