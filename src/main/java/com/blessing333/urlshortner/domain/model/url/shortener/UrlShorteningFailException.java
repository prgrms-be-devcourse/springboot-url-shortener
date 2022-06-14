package com.blessing333.urlshortner.domain.model.url.shortener;

public class UrlShorteningFailException extends RuntimeException {
    public UrlShorteningFailException(Throwable cause) {
        super(cause);
    }
}
