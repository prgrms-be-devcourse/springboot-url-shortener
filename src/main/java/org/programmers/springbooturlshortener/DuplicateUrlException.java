package org.programmers.springbooturlshortener;

public final class DuplicateUrlException extends IllegalArgumentException {
    public DuplicateUrlException(String original) {
        super("이미 등록된 url입니다:" + original);
    }
}