package com.jhs.shortenerurl.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
public class Url {

    @Id
    @GeneratedValue
    private Long id;

    private String originUrl;

    private String shortenUrl;

    protected Url() {
    }

    public Url(String originUrl) {
        validateNotNull(originUrl);

        this.originUrl = originUrl;
    }

    private void validateNotNull(String target) {
        if (target.isBlank()) {
            throw new IllegalArgumentException("빈 값일 수 없습니다");
        }
    }

    public void assignShortenUrl(String shortenUrl) {
        validateNotNull(shortenUrl);

        this.shortenUrl = shortenUrl;
    }

    public Long getId() {
        return id;
    }

}
