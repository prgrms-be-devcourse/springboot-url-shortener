package com.prgrms.short_url.exception;

public class NotFoundException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE = "찾는 url이 없습니다.";

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
