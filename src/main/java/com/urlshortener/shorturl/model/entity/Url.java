package com.urlshortener.shorturl.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "url")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "origin_url", length = 500, unique = true, nullable = false)
    private String originUrl;

    @Column(name = "short_url", length = 10, unique = true, nullable = false)
    private String shortUrl;

    @Column(name = "count")
    private Integer count;

    public Url(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.count = 0;
    }
}
