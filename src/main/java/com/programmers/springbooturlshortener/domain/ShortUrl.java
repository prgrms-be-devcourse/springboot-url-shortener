package com.programmers.springbooturlshortener.domain;

import javax.persistence.*;

@Entity
@Table(
        name = "short_url",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk",
                        columnNames = {"algorithm", "origin_url"}
                )
        }
)
public class ShortUrl {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "algorithm", length = 20)
    private String algorithm;

    @Column(name = "origin_url", length = 2000)
    private String originUrl;

    @Column(name = "short_url", length = 7)
    private String shortUrl;

    @Column(name = "request_count")
    private Long requestCount;
}
