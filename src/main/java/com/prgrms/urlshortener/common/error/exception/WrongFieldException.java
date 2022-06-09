package com.prgrms.urlshortener.common.error.exception;

import lombok.Getter;

@Getter
public class WrongFieldException extends RuntimeException {
    private String field;
    private Object value;
    private String reason;

    public WrongFieldException(String field, Object value, String reason) {
        super(reason);
        this.field = field;
        this.value = value;
        this.reason = reason;
    }
}
