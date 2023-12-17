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
    @Column(name = "url_id")
    private Long id;

    @Column(name = "origin_url", unique = true, nullable = false)
    private String originUrl;

    @Column(name = "request_count")
    private int requestCount;

    public Url(String originUrl) {
        this.originUrl = originUrl;
        this.requestCount = 1;
    }

    public void increaseRequestCount() {
        requestCount++;
    }
}
