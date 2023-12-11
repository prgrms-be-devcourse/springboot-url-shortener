package com.programmers.springbooturlshortener.domain.exception;

import static com.programmers.springbooturlshortener.common.exception.ExceptionMessage.LONG_URL_FORMAT_MISMATCH;

public class LongUrlFormatMismatchException extends RuntimeException {
    public LongUrlFormatMismatchException() {
        super(LONG_URL_FORMAT_MISMATCH.getValue());
    }
}
