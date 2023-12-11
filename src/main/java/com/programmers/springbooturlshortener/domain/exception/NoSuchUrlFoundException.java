package com.programmers.springbooturlshortener.domain.exception;

import static com.programmers.springbooturlshortener.common.exception.ExceptionMessage.NO_SUCH_URL_FOUND;

public class NoSuchUrlFoundException extends RuntimeException{
    public NoSuchUrlFoundException() {
        super(NO_SUCH_URL_FOUND.getValue());
    }
}
