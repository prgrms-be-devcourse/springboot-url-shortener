package com.prgrms.shorturl.url.exception;

import com.prgrms.shorturl.global.error.ErrorCode;

import lombok.Getter;

@Getter
public class ExistedOriginUrlException extends RuntimeException {

    private final ErrorCode errorCode;

    public ExistedOriginUrlException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
