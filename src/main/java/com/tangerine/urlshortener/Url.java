package com.tangerine.urlshortener;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OriginUrl originUrl;

    @Embedded
    private ShortUrl shortUrl;

    @Embedded
    private Algorithm algorithm;

    @Embedded
    private RequestCount requestCount;

    public Url(OriginUrl originUrl, ShortUrl shortUrl, Algorithm algorithm, RequestCount requestCount) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.algorithm = algorithm;
        this.requestCount = requestCount;
    }

    protected Url() {
    }

    public Long getId() {
        return id;
    }

    public OriginUrl getOriginUrl() {
        return originUrl;
    }

    public String getOriginUrlText() {
        return originUrl.getOriginUrlText();
    }

    public ShortUrl getShortUrl() {
        return shortUrl;
    }

    public String getShortUrlText() {
        return shortUrl.getShortUrlText();
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public RequestCount getRequestCount() {
        return requestCount;
    }

}
