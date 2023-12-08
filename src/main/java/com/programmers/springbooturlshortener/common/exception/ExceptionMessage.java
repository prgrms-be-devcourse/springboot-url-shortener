package com.programmers.springbooturlshortener.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    NO_SUCH_URL_FOUND("일치하는 URL이 존재하지 않습니다."),
    LONG_URL_FORMAT_MISMATCH("URL 포멧이 일치하지 않습니다.");

    private final String value;
}
