package org.daehwi.shorturl.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ShortUrl {

    @Id
    private Long id;

    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Column(name = "request_count", nullable = false)
    private Long requestCount;

    public ShortUrl(Long id, String originUrl, String shortUrl) {
        this.id = id;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.requestCount = 0L;
    }

    public void increaseRequestCount() {
        ++this.requestCount;
    }
}
