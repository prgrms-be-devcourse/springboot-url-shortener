package com.example.urlmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UrlErrorCode {
    ENCODE_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 EncodeType이 존재하지 않습니다."),
    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 url 정보가 존재하지 않습니다."),
    INVALID_URL(HttpStatus.BAD_REQUEST, "올바르지 않은 형식의 URL입니다.");

    private final HttpStatus errorHttpStatus;
    private String errorMessage;

    UrlErrorCode(HttpStatus errorHttpStatus, String errorMessage) {
        this.errorHttpStatus = errorHttpStatus;
        this.errorMessage = errorMessage;
    }

    public void updateErrorMessage(String wrongInput) {
        this.errorMessage = this.errorMessage + " : " + wrongInput;
    }
}
