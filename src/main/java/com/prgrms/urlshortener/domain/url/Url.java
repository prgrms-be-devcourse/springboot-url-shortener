package com.prgrms.urlshortener.domain.url;

import com.prgrms.urlshortener.domain.BaseEntity;
import com.prgrms.urlshortener.dto.ShortenUrlResponse;
import com.prgrms.urlshortener.dto.UrlResponse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static com.prgrms.urlshortener.common.util.Base62.encoding;
import static com.prgrms.urlshortener.common.util.Validator.validateRequestCount;
import static com.prgrms.urlshortener.common.util.Validator.validateUrl;

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

    public UrlResponse getUrlResponse() {
        return new UrlResponse(url);
    }

    public ShortenUrlResponse getShortenUrlResponse() {
        return new ShortenUrlResponse(encoding(id), url, requestCount, super.createdAt);
    }

    public void increaseRequestNumber() {
        this.requestCount++;
    }
}
