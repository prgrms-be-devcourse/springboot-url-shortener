package com.kdt.shortener.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "urlinfo")
public class UrlInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String originUrl;

    public UrlInfo(String originUrl) {
        this.originUrl = originUrl;
    }

}
