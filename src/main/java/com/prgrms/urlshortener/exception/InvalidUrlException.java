package com.prgrms.urlshortener.exception;

public class InvalidUrlException extends InvalidException {

    private static final String MESSAGE_FORMAT = "%s은 잘못된 URL 포맷입니다.";

    public InvalidUrlException(String url) {
        super(MESSAGE_FORMAT.formatted(url));
    }

}
