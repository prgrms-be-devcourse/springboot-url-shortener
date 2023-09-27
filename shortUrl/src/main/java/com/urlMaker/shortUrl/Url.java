package com.urlMaker.shortUrl;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "urls")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue
    private Long urlId;

    @Column(nullable = false)
    private String originUrl;

    @Column(nullable = false)
    private String algorithm;

    @Column(name = "request_count")
    private Integer requestCount;

    @Builder
    public Url(String originUrl, String algorithm) {
        this.originUrl = originUrl;
        this.algorithm = algorithm;
        this.requestCount = 0;
    }

    public void increaseRequestCount() {
        this.requestCount += 1;
    }

}
