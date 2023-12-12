package com.pgms.shorturlcoredomain.exception;

public class NoUrlException extends CustomException{
    public NoUrlException() {
        super("404/00001", "존재하지 않는 URL 입니다");
    }
}
