package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "urls")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "original_url")
    private String originalUrl;

    @Column(unique = true)
    private String encoded; // index를 base62로 인코딩 한 결과

    public Url(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }
}
