package com.programmers.springbooturlshortener.domain;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "url",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk",
                        columnNames = {"algorithm", "origin_url"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", length = 20)
    private Algorithm algorithm;

    @Column(name = "origin_url", length = 2000)
    private String originUrl;

    @Column(name = "short_url", length = 7)
    private String shortUrl;

    @Column(name = "request_count")
    private Long requestCount;

    @Builder
    public Url(Algorithm algorithm, String originUrl, String shortUrl, Long requestCount) {
        this.algorithm = algorithm;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.requestCount = requestCount;
    }

    public void increaseRequestCount() {
        this.requestCount++;
    }
}
