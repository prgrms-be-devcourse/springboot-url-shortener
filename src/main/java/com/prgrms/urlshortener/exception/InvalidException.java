package com.prgrms.urlshortener.exception;

import org.springframework.http.HttpStatus;

public class InvalidException extends ApplicationException {

    public InvalidException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
