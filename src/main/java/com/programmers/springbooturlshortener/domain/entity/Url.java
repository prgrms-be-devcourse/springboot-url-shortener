package com.programmers.springbooturlshortener.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "url")
public class Url {
    @Id
    @Column(nullable = false, updatable = false, length = 7)
    private String shortUrl;

    @Column(nullable = false)
    private String longUrl;

    @Enumerated
    @Column(name = "encode_type")
    private EncodeType encodeType;

    @Column(nullable = false)
    private Long hit = 0L;

    @Builder
    private Url(String longUrl, String shortUrl, EncodeType encodeType) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.encodeType = encodeType;
    }

    public void updateHit() {
        hit += 1;
    }

}