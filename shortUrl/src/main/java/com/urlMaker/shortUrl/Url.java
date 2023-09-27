package com.urlMaker.shortUrl;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.aot.generate.GeneratedTypeReference;

@Entity
@Table(name = "urls")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    private Long urlId;

    @Column(
            name = "origin_url",
            nullable = false
    )
    private String originUrl;

    @Column(
            name = "algorithm",
            nullable = false
    )
    private String algorithm;

    @Column(
            name = "request_count",
            nullable = false
    )
    private Integer requestCount;

    @Builder
    public Url(
            String originUrl,
            String algorithm
    ) {
        this.originUrl = originUrl;
        this.algorithm = algorithm;
        this.requestCount = 0;
    }

    public void increaseRequestCount() {
        this.requestCount += 1;
    }
//
//    public void setUrlId(Long urlId) {
//        this.urlId = urlId
//    }

}
