package com.prgrms.wonu606.shorturl.service;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String message) {
        super(message);
    }
}
