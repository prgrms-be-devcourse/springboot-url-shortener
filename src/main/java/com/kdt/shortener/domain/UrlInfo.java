package com.kdt.shortener.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "urlinfo")
public class UrlInfo {

    @Id
    private Long id;

    private String originUrl;

    public UrlInfo(String originUrl) {
        this.originUrl = originUrl;
    }

}
