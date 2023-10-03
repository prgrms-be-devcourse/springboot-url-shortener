package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    private String encoded; // index를 base62로 인코딩 한 결과

    public Url(String url) {
        this.url = url;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }
}
