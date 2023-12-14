package org.prgms.springbooturlshortener.domain.shorturl.exception;

public class UrlException extends RuntimeException {
    private UrlErrorCode urlErrorCode;

    public UrlException(UrlErrorCode urlErrorCode) {
        this.urlErrorCode = urlErrorCode;
    }
}
