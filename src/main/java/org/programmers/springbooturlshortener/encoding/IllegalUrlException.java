package org.programmers.springbooturlshortener.encoding;

public class IllegalUrlException extends IllegalArgumentException {
    public IllegalUrlException(String shortenUrl) {
        super("잘못된 형식의 url입니다: " + shortenUrl);
    }
}
