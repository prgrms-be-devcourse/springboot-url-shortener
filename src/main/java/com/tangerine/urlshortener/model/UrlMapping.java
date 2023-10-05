package com.tangerine.urlshortener.model;

import com.tangerine.urlshortener.model.vo.Algorithm;
import com.tangerine.urlshortener.model.vo.OriginUrl;
import com.tangerine.urlshortener.model.vo.RequestCount;
import com.tangerine.urlshortener.model.vo.ShortUrl;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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

    @Embedded
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

    public String getAlgorithmText() {
        return algorithm.getAlgorithm();
    }

    public RequestCount getRequestCount() {
        return requestCount;
    }

    public long getRequestCountValue() {
        return requestCount.getRequestCount();
    }

}
