package org.prgms.springbooturlshortener.domain.shorturl.exception;

import lombok.Getter;

@Getter
public enum UrlErrorCode {
    ORIGINAL_URL_NOT_FOUND(1100);

    private final int errorCode;

    UrlErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
