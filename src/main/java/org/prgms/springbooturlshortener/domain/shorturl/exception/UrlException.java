package org.prgms.springbooturlshortener.domain.shorturl.exception;

public class UrlException extends RuntimeException {
    private final UrlErrorCode urlErrorCode;

    public UrlException(UrlErrorCode urlErrorCode) {
        this.urlErrorCode = urlErrorCode;
    }
}
