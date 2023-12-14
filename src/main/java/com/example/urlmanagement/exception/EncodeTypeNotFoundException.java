package com.example.urlmanagement.exception;

public class EncodeTypeNotFoundException extends RuntimeException {

    public EncodeTypeNotFoundException(String wrongType) {
        super("해당하는 EncodeType이 존재하지 않습니다. : " + wrongType);
    }
}
