package com.prgrms.shorturl.exception;

public class UrlException extends IllegalArgumentException{
    public UrlException(String url) {
        super("잘못된 Url 입니다. \n Url: " + url);
    }
}
