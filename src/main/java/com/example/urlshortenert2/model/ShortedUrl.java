package com.example.urlshortenert2.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String shorteningKey;
    @Column(nullable = false)
    private String originUrl;

    public ShortedUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public void setShorteningKey(String shorteningKey) {
        this.shorteningKey = shorteningKey;
    }
}
