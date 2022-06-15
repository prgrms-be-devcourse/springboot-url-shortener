package com.prgrms.short_url.exception;

public class NoConnectUrlException extends RuntimeException {
    private static final String NO_CONNECTION = "주어진 url이 연결이 되지 않습니다.";

    public NoConnectUrlException() {
        super();
    }

    public NoConnectUrlException(String message) {
        super(message);
    }

}
