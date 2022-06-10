package com.taehan.urlshortener.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.taehan.urlshortener.model.RegexPattern.ORIGINAL_URL;
import static com.taehan.urlshortener.model.RegexPattern.SHORT_URL;

@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp=ORIGINAL_URL)
    private String originalUrl;

    @Column(nullable = false, length = 8, unique = true)
    @Size(min = 1, max = 8)
    @Pattern(regexp = SHORT_URL)
    private String shortUrl;

    @ColumnDefault(value = "0")
    @Min(0)
    private int count;

    @Enumerated(EnumType.STRING)
    private AlgorithmType algorithm;

    protected Url() {
    }

    public Url(String originalUrl, String shortUrl, AlgorithmType algorithm) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.algorithm = algorithm;
    }

    public void addCount() {
        count++;
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public int getCount() {
        return count;
    }

    public AlgorithmType getAlgorithm() {
        return algorithm;
    }

    public void changeShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
