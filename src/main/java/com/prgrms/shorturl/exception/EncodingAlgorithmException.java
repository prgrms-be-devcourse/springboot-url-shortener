package com.prgrms.shorturl.exception;

import com.prgrms.shorturl.domain.EncodingFactory;

public class EncodingAlgorithmException extends RuntimeException {
    public EncodingAlgorithmException(String message) {
        super(message);
    }
}
