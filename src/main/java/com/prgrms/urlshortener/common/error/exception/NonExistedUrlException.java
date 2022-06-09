package com.prgrms.urlshortener.common.error.exception;

public class NonExistedUrlException extends RuntimeException{
    public NonExistedUrlException() {
        super("can't find a url");
    }
}
