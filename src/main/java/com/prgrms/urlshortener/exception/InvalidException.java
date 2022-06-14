package com.prgrms.urlshortener.exception;

import org.springframework.http.HttpStatus;

import com.prgrms.urlshortener.common.exception.ApplicationException;

public class InvalidException extends ApplicationException {

    public InvalidException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
