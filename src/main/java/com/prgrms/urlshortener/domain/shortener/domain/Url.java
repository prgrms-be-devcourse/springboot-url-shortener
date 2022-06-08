package com.prgrms.urlshortener.domain.shortener.domain;

import com.prgrms.urlshortener.global.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static com.prgrms.urlshortener.domain.shortener.util.UrlValidator.validateRequestCount;
import static com.prgrms.urlshortener.domain.shortener.util.UrlValidator.validateUrl;

@Entity
public class Url extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private long requestCount;

    protected Url() {

    }

    public Url(String url, long requestCount) {
        validateUrl(url);
        validateRequestCount(requestCount);

        this.url = url;
        this.requestCount = requestCount;
    }

    public void increaseRequestNumber() {
        this.requestCount++;
    }
}
