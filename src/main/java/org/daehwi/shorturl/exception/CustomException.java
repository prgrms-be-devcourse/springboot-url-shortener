package org.daehwi.shorturl.exception;


import org.daehwi.shorturl.controller.dto.ResponseStatus;

public class CustomException extends GeneralException {
    public CustomException(ResponseStatus errorResponseStatus) {
        super(errorResponseStatus);
    }
}
