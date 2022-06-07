package com.taehan.urlshortener.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String encodedUrl;

    @ColumnDefault(value = "0")
    private int count;

    @Enumerated(EnumType.STRING)
    private AlgorithmType algorithm;

    protected Url() {
    }

    public Url(String url, String encodedUrl, int count, AlgorithmType algorithm) {
        this.url = url;
        this.encodedUrl = encodedUrl;
        this.count = count;
        this.algorithm = algorithm;
    }

    public void addCount() {
        count++;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getEncodedUrl() {
        return encodedUrl;
    }

    public int getCount() {
        return count;
    }

    public AlgorithmType getAlgorithm() {
        return algorithm;
    }
}
