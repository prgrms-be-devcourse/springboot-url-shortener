package com.devcourse.shorturl.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_EXIST_SHORT_URL("존재하지 않는 단축 URL입니다.");
    private final String message;
}
