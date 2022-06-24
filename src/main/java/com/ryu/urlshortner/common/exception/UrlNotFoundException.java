package com.ryu.urlshortner.common.exception;

public class UrlNotFoundException extends BusinessException {
    private static final ErrorCode errorCode = ErrorCode.URL_NOT_FOUND;

    public UrlNotFoundException() {
        super(errorCode);
    }
}
