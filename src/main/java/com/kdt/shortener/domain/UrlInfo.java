package com.kdt.shortener.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "urlinfo")
public class UrlInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "origin_url", unique = true, length = 1000)
    private String originUrl;

    public UrlInfo(String originUrl) {
        this.originUrl = originUrl;
    }

}
