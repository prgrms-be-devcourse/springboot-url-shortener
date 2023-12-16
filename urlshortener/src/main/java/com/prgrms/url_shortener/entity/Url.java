package com.prgrms.url_shortener.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Url {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "url_name")
    private Long id;

    @Column(name = "origin_url", unique = true,  nullable = false)
    private String originUrl;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "request_count")
    private int requestCount;

    public Url(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.requestCount = 0;
    }
}
