package com.prgrms.shorturl.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //global
    INTERNAL_SERVER_ERROR("G001", "Internal Server Error"),

    INVALID_INPUT_VALUE_ERROR("G002", "유효하지 않은 입력값입니다."),
    INVALID_METHOD_ERROR("G003", "Method Argument가 적절하지 않습니다."),
    REQUEST_BODY_MISSING_ERROR("G004", "RequestBody에 데이터가 존재하지 않습니다."),
    REQUEST_PARAM_MISSING_ERROR("G005", "RequestParam에 데이터가 전달되지 않았습니다."),

    //url
    DUPLICATED_CREATE_ORIGIN_URL_ERROR("U001", "이미 존재하는 origin Url입니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
