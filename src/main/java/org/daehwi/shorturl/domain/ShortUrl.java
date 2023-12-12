package org.daehwi.shorturl.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ShortUrl {

    @Id
    private Long id;

    @Column(name = "base_url", nullable = false)
    private String baseUrl;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;
}
