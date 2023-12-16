package com.programmers.urlshortener.domain;

import com.programmers.urlshortener.util.encoder.Algorithm;
import com.programmers.urlshortener.util.encoder.UrlEncoder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name = "url_key", nullable = false, unique = true)
    private String urlKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", nullable = false)
    private Algorithm algorithm;

    @Column(name = "request_count", nullable = false)
    private Long requestCount;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    public ShortUrl(UrlEncoder urlEncoder, String originUrl) {
        this.originUrl = originUrl;
        this.urlKey = urlEncoder.encode(originUrl);
        this.algorithm = urlEncoder.getAlgorithm();
        this.requestCount = 0L;
        this.createdAt = LocalDateTime.now();
    }

    public void increaseRequestCount() {
        this.requestCount++;
    }
}
