package com.prgrms.springbooturlshortener.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "url")
@NoArgsConstructor
@SequenceGenerator(
        name = "url_sequence",
        sequenceName = "url_sequence",
        initialValue = 10000,
        allocationSize = 100
)
public class Url {

    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "short_url")
    private String shortUrl;

    public Url(String originalUrl) {
        this.originalUrl = removeProtocol(originalUrl);
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    private String removeProtocol(String originalUrl) {
        if (originalUrl.startsWith(HTTPS)) {
            return originalUrl.replace(HTTPS, "");
        }

        if (originalUrl.startsWith(HTTP)) {
            return originalUrl.replace(HTTP, "");
        }

        return originalUrl;
    }
}
