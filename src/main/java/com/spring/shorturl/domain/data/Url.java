package com.spring.shorturl.domain.data;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "short_url")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Url {
    @Id
    @Column(name = "url_id", updatable = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "shart_url")
    @Size(max = 100)
    private String shortUrl;

    @Column(name = "origin_url")
    @Size(max = 500)
    private String originUrl;

    @Column(name = "requset_count")
    private int requestCount = 0;

    @Builder
    public Url(Long id, String shortUrl, String originUrl, int requestCount) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.originUrl = originUrl;
        this.requestCount = requestCount;
    }

    public void changeShortUrl(String shortUrl){
        this.shortUrl = shortUrl;
    }

    public void addRequstCount(){
        requestCount++;
    }
}
