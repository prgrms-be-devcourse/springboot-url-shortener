package com.kdt.shortener.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "urlinfo")
public class UrlInfo {

    @Id
    private Long id;

    private String originUrl;

}
