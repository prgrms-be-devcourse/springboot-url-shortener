package com.prgrms.shorturl.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_URL("U001", "해당 URL을 찾을 수 없습니다"),
    INVALID_URL_FORMAT("u002", "올바른 URL 형식이 아닙니다.");

    private String code;
    private String errorMessage;
}
