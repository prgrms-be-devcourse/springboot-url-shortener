package com.prgrms.urlshortener.domain;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Urls {
    private final UUID id;
    private final String originUrl;
    private final String shortenUrl;
    private AtomicLong count;
//커밋 단위
//1. 화면
//2. 단축 알고리즘
//3. 단축 엔드포인트
//4. 처리율 제한
//5. 캐싱 처리
    public Urls(String originUrl, String shortenUrl) {
        this.id = UUID.randomUUID();
        this.originUrl = originUrl;
        this.shortenUrl = shortenUrl;
        this.count = new AtomicLong(1L);
    }

    public String getShortenUrl() {
        return shortenUrl;
    }

    public Long getCount() {
        return count.get();
    }

    public void addCount() {
        this.count.incrementAndGet();
    }
}