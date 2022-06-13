package com.prgrms.urlshortener.exception;

// TODO : 인코딩이 잘못되었을 때를 생각하여 RuntimeException 상속하는 것이 맞을까?
public class InvalidShortedUrlException extends RuntimeException {

    public InvalidShortedUrlException(String message) {
        super(message);
    }

}
