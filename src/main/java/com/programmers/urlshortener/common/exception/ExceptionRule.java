package com.programmers.urlshortener.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionRule {

    SHORTENURL_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 URL은 단축된 URL 목록에서 찾을 수 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 URL은 없는 URL입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다. 입력 값을 다시 확인해주세요."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 알 수 없는 오류가 발생했습니다. 관리자에게 문의해주세요."),
    ;

    private final HttpStatus status;
    private final String message;
}
