package org.prgms.springbooturlshortener.domain.shorturl.exception;

public enum UrlErrorCode {
    ORIGINAL_URL_NOT_FOUND(400);

    private final int errorCode;

    UrlErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
