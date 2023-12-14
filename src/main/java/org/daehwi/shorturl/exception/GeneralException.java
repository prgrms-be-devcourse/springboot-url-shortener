package org.daehwi.shorturl.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.daehwi.shorturl.controller.dto.ResponseStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralException extends RuntimeException {
    private ResponseStatus errorResponseStatus;

    public GeneralException(ResponseStatus errorResponseStatus) {
        super(errorResponseStatus.getMessage());
        this.errorResponseStatus = errorResponseStatus;
    }

    public ResponseStatus getErrorResponseStatus() {
        return errorResponseStatus;
    }
}
