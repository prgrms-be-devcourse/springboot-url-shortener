package com.prgrms.short_url.domain;

import com.sun.istack.NotNull;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "original_url")
    @NotNull
    @Lob
    private String originalUrl;

    @Column(name = "short_url")
    @NotNull
    private String shortUrl;

    public Url(int id, String originalUrl, String shortUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }

    public Url() {
    }

    public int getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

}
