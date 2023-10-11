package com.programmers.urlshortener.url.domain;

import static com.programmers.urlshortener.common.exception.ExceptionRule.*;
import static com.programmers.urlshortener.common.validator.UrlValidator.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.programmers.urlshortener.common.exception.UrlException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Algorithm algorithm;

    @Column(nullable = false)
    private String originalUrl;

    @Column(unique = true)
    private String shortUrl;

    @Column(nullable = false)
    private String ip;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long viewCount;

    @Builder
    public Url(String originalUrl, Algorithm algorithm, String ip) {
        validateUrlPattern(originalUrl);
        this.algorithm = algorithm;
        this.originalUrl = originalUrl;
        this.ip = ip;
    }

    public void convertToShortUrl() {
        if (id == null) {
            throw new UrlException(URL_NOT_SAVED);
        }

        this.shortUrl = algorithm.getShortUrl(id.intValue());
    }

    public void increaseCount() {
        viewCount += 1;
    }
}
