package com.prgms.shorturl.url.exception;

public class NotFoundUrlException extends RuntimeException{

    public NotFoundUrlException() {
    }

    public NotFoundUrlException(String message) {
        super(message);
    }

    public NotFoundUrlException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUrlException(Throwable cause) {
        super(cause);
    }

    public NotFoundUrlException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
