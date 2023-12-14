package com.programmers.urlshortener.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    @Column(name = "value", nullable = false, unique = true)
    String value;

    @Column(name = "origin_url", nullable = false)
    String originUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", nullable = false)
    Algorithm algorithm;

    @Column(name = "request_count", nullable = false)
    Long requestCount;

    @CreatedDate
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdAt;
}
