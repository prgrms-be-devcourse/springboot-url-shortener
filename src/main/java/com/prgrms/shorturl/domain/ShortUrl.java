package com.prgrms.shorturl.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @Id
    @Column(name = "short_url_id")
    private String id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(nullable = false)
    private String base62Url;
}
