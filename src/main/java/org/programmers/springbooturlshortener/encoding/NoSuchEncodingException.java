package org.programmers.springbooturlshortener.encoding;

public class NoSuchEncodingException extends IllegalArgumentException {
    public NoSuchEncodingException(char prefix) {
        super("인코딩 prefix 코드" + prefix + " 에 해당하는 인코딩은 없습니다.");
    }
}