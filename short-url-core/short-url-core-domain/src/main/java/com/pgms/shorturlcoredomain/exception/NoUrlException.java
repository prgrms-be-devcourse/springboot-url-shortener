package com.pgms.shorturlcoredomain.exception;

public class NoUrlException extends CustomException{
    public NoUrlException(int code, String message) {
        super("404/00001", message);
    }
}
