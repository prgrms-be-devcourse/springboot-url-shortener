package com.ryu.urlshortner.common.exception;

public class UrlExpiredException extends BusinessException {
    private static final ErrorCode errorCode = ErrorCode.URL_EXPIRED;

    public UrlExpiredException() {
        super(errorCode);
    }
}
