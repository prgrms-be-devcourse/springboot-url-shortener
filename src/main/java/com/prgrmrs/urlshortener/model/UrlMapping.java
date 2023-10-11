package com.prgrmrs.urlshortener.model;

import com.prgrmrs.urlshortener.model.vo.OriginalUrl;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UrlMapping {

    @Embedded
    private final OriginalUrl originalUrl;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hash")
    private String hash;

    @Column(name = "view_count")
    private Long viewCount;

    public UrlMapping(OriginalUrl originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void embedHash(String createdHash) {
        this.hash = createdHash;
    }

    public OriginalUrl getOriginalUrl() {
        return originalUrl;
    }

    public String getHash() {
        return hash;
    }

    public Long getSequence() {
        return id;
    }

    public void increaseViewCount() {
        this.viewCount += 1;
    }
}