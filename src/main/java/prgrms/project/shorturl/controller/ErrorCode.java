package prgrms.project.shorturl.controller;

import lombok.Getter;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(BAD_REQUEST.value(), "잘못된 값을 입력하였습니다."),

    NOT_SUPPORTED_MEDIA_TYPE(UNSUPPORTED_MEDIA_TYPE.value(), "지원하지 않는 미디어 타입입니다."),

    TYPE_MISMATCH(BAD_REQUEST.value(), "잘못된 타입을 입력하였습니다."),

    ENTITY_NOT_FOUND(NOT_FOUND.value(), "요청한 엔티티를 찾을 수 없습니다."),

    MISSING_QUERY_PARAMETER(BAD_REQUEST.value(), "필요한 파라미터가 존재하지 않습니다"),

    SERVER_ERROR(INTERNAL_SERVER_ERROR.value(), "알 수 없는 서버오류가 발생하였습니다.");

    private final int statusCode;

    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
