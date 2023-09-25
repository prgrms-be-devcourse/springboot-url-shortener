package com.urlMaker.shortUrl;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "urls")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "origin_url")
    private String originUrl;

    @Column(name="shorten_url")
    private String shortenUrl;

    @Column(name="request_count")
    private Long requestCount;


}
