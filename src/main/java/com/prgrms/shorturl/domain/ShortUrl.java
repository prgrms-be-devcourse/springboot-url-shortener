package com.prgrms.shorturl.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ShortUrl {

    @Id @GeneratedValue
    @Column(name = "short_url_id")
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(nullable = false)
    private String base62Url;

    public ShortUrl(String originalUrl, String base62Url) {
        this.originalUrl = originalUrl;
        this.base62Url = base62Url;
    }
}
