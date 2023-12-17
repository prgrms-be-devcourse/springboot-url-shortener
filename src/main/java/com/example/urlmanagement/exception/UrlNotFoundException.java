package com.example.urlmanagement.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String wrongUrl) {
        super(wrongUrl + "에 해당하는 url 정보가 존재하지 않습니다.");
    }
}
