package com.prgrms.urlshortener.domain;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Url {

    private static final String URL_REGEX = "^(https?:\\/\\/)?([\\da-z\\.-]+\\.[a-z\\.]{2,6}|[\\d\\.]+)([\\/:?=&#]{1}[\\da-z\\.-]+)*[\\/\\?]?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    private static final int MAX_SHORTEN_URL_LENGTH = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originUrl;

    @Column(unique = true, length = MAX_SHORTEN_URL_LENGTH)
    private String shortenUrl;

    @Column(nullable = false)
    private long requestCount = 0L;

    protected Url() {
    }

    public Url(String originUrl) {
        validateUrl(originUrl);
        this.originUrl = originUrl;
    }

    private void validateUrl(String url) {
        if (!URL_PATTERN.matcher(url).matches()) {
            throw new IllegalArgumentException("%s은 잘못된 URL 포맷입니다.".formatted(url));
        }
    }

    public void assignShortenUrl(String shortenUrl) {
        validateAssigned();
        validateShortenUrl(shortenUrl);
        this.shortenUrl = shortenUrl;
    }

    public void addReqeustCount() {
        this.requestCount++;
    }

    private void validateAssigned() {
        if (Objects.nonNull(this.shortenUrl)) {
            throw new IllegalArgumentException("이미 단축 URL이 할당되어 있습니다.");
        }
    }

    private void validateShortenUrl(String shortenUrl) {
        validateEmpty(shortenUrl);
        validateLength(shortenUrl);
    }

    private void validateLength(String shortenUrl) {
        if (shortenUrl.length() > 8) {
            throw new IllegalArgumentException("단축 URL은 8자를 넘을 수 없습니다.");
        }
    }

    private void validateEmpty(String shortenUrl) {
        if (Objects.isNull(shortenUrl) || shortenUrl.isBlank()) {
            throw new IllegalArgumentException("단축 URL은 비어있을 수 없습니다.");
        }
    }

}
