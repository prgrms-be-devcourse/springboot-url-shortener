package com.example.urlmanagement.exception;

public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException(String wrongUrl) {
        super("올바르지 않은 형식의 URL입니다. : " + wrongUrl);
    }
}
