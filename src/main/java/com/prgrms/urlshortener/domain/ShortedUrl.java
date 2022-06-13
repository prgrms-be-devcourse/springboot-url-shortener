package com.prgrms.urlshortener.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.prgrms.urlshortener.exception.InvalidShortedUrlException;

@Embeddable
public class ShortedUrl {

    private static final int MAX_SHORTED_URL_LENGTH = 8;

    @Column(name = "shorted_url", unique = true, length = MAX_SHORTED_URL_LENGTH)
    private String url;

    protected ShortedUrl() {
    }

    public ShortedUrl(String url) {
        validateUrl(url);
        this.url = url;
    }

    private void validateUrl(String url) {
        validateEmpty(url);
        validateLength(url);
    }

    private void validateEmpty(String url) {
        if (Objects.isNull(url) || url.isBlank()) {
            throw new InvalidShortedUrlException("단축된 URL은 비어있을 수 없습니다.");
        }
    }

    private void validateLength(String url) {
        if (url.length() > MAX_SHORTED_URL_LENGTH) {
            throw new InvalidShortedUrlException("단축된 URL은 8자를 넘을 수 없습니다.");
        }
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ShortedUrl shortedUrl = (ShortedUrl)o;
        return Objects.equals(getUrl(), shortedUrl.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
