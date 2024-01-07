package com.example.shorturl.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Url {

    private static final int INITIAL_REQUEST_COUNT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_sequence_generator")
    @SequenceGenerator(name = "url_sequence_generator", sequenceName = "url_sequencer", allocationSize = 1)
    @Column(name = "url_id")
    private Long id;

    @Column(name = "origin_url", updatable = false, nullable = false)
    private String originUrl;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "request_count")
    private int requestCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", nullable = false)
    private Algorithm algorithm;

    public Url(String originUrl, Algorithm algorithm) {
        this.originUrl = originUrl;
        this.algorithm = algorithm;
        this.requestCount = INITIAL_REQUEST_COUNT;
    }

    public void updateShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void increaseRequestCount() {
        this.requestCount += 1;
    }
}
