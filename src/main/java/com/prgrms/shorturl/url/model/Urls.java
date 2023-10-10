package com.prgrms.shorturl.url.model;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Urls {

    private static final String SHORT_URL_DEFAULT_BLANK = ".";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originUrl;

    @Column
    @Embedded
    private ShortUrl shortUrl;

    @Column
    @Embedded
    private RequestCount requestCount;

    public Urls(String originUrl) {
        isNotBlank(originUrl);
        this.originUrl = originUrl;
        this.shortUrl = new ShortUrl(SHORT_URL_DEFAULT_BLANK);
        this.requestCount = new RequestCount(0);
    }

    public void registerShortUrl(ShortUrl shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getRequestCount() {
        return requestCount.getRequestCount();
    }

    public String getShortUrl() {
        return shortUrl.getShortUrl();
    }

    public String getOriginUrl() {
        addRequestCount();
        return originUrl;
    }

    private void addRequestCount() {
        requestCount.plusOneCount();
    }

    private void isNotBlank(String originUrl) {
        if (originUrl == null || originUrl.isBlank()) {
            throw new IllegalArgumentException("origin URL은 null이거나 빈 값일 수 없습니다.");
        }
    }

}
