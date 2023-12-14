package com.example.urlmanagement.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String wrongShortUrl) {
        super(wrongShortUrl + "에 해당하는 original url이 존재하지 않습니다.");
    }
}
