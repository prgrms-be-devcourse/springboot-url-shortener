package com.progms.shorturl.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Getter
@Entity(name = "short_urls")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "short_id")
    private Long shortId;

    @Column(name = "origin_url")
    private String originUrl;

    @Column(name = "shorten_url")
    private String shortenUrl;

    @Column(name = "views")
    private long views;

    private ShortUrl(String originUrl) {
        this.originUrl = requireNonNull(originUrl);
        this.views = 0;
    }

    public static ShortUrl createShortUrl(String originUrl) {
        return new ShortUrl(originUrl);
    }

    public void updateShortUrl(String shortenUrl) {
        this.shortenUrl = requireNonNull(shortenUrl);
    }
}
