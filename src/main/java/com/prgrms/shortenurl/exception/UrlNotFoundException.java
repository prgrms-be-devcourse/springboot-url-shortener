package com.prgrms.shortenurl.exception;

public class UrlNotFoundException extends IllegalArgumentException {
    private String message;

    public UrlNotFoundException() {
        this.message = "해당 키에 맞는 url을 찾을 수 없습니다.";
    }
}
