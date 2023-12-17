package com.example.urlmanagement.domain;

import com.example.urlmanagement.exception.UrlErrorCode;
import com.example.urlmanagement.exception.UrlException;
import com.example.urlmanagement.global.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.MalformedURLException;
import java.net.URL;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Url extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;

    private String shortUrl;

    private Integer requestCount;

    @Builder
    public Url(String originalUrl) {
        validateOriginalUrl(originalUrl);
        this.originalUrl = originalUrl;
        this.requestCount = 0;
    }

    public void updateShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void increaseRequestCount() {
        this.requestCount += 1;
    }

    private void validateOriginalUrl(String originalUrl) {
        try {
            new URL(originalUrl);
        } catch (MalformedURLException e) {
            throw new UrlException(UrlErrorCode.INVALID_URL, originalUrl);
        }
    }
}
