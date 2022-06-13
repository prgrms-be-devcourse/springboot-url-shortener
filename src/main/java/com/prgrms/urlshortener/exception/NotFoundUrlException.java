package com.prgrms.urlshortener.exception;

import org.springframework.http.HttpStatus;

import com.prgrms.urlshortener.common.exception.ApplicationException;

public class NotFoundUrlException extends ApplicationException {

    private static final String MESSAGE_FORMAT = "%s로 저장된 URL이 없습니다.";

    public NotFoundUrlException(String shortedUrl) {
        super(HttpStatus.NOT_FOUND, MESSAGE_FORMAT.formatted(shortedUrl));
    }

}
