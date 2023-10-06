package com.tangerine.urlshortener.url.model;

import com.tangerine.urlshortener.url.model.vo.Algorithm;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.model.vo.RequestCount;
import com.tangerine.urlshortener.url.model.vo.ShortUrl;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "url_mappings")
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OriginUrl originUrl;

    @Embedded
    private ShortUrl shortUrl;

    @Enumerated(value = EnumType.STRING)
    private Algorithm algorithm;

    @Embedded
    private RequestCount requestCount;

    public UrlMapping(OriginUrl originUrl, ShortUrl shortUrl, Algorithm algorithm, RequestCount requestCount) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.algorithm = algorithm;
        this.requestCount = requestCount;
    }

    protected UrlMapping() {
    }

    public Long getId() {
        return id;
    }

    public OriginUrl getOriginUrl() {
        return originUrl;
    }

    public ShortUrl getShortUrl() {
        return shortUrl;
    }

    public Algorithm getAlgorithmName() {
        return algorithm;
    }

    public RequestCount getRequestCount() {
        return requestCount;
    }

    public void setShortUrl(ShortUrl shortUrl) {
        this.shortUrl = shortUrl;
    }

    public UrlMapping addRequestCount() {
        this.requestCount = RequestCount.addCount(requestCount);
        return this;
    }
}
