package com.ryu.urlshortner.common.exception;

public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "적절하지 않은 요청 값입니다."),
    INVALID_TYPE_VALUE(400,  "요청 값의 타입이 잘못되었습니다."),
    URL_NOT_FOUND(400,  "URL을 찾을 수 없습니다."),
    URL_EXPIRED(400,  "URL 기간이 만료되었습니다."),

    INTERNAL_SERVER_ERROR(500,  "서버 내부에 오류가 생겼습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
