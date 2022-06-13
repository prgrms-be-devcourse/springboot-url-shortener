package org.programmers.springbooturlshortener.service;

public class NoUrlFoundException extends IllegalArgumentException {
    public NoUrlFoundException(String shortenUrl, Long key) {
        super(shortenUrl + "의 key \"" + key + "\"에 해당하는 저장된 Url을 찾을 수 없습니다.");
    }
}