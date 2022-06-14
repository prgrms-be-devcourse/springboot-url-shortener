package com.prgrms.urlshortener.exception;

public class InvalidEncodeTypeException extends InvalidException {

    private static final String MESSAGE_FORMAT = "%s은 지원하는 인코딩이 아닙니다.";

    public InvalidEncodeTypeException(String encodeType) {
        super(MESSAGE_FORMAT.formatted(encodeType));
    }

}
