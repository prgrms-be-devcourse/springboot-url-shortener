package com.urlmaker.url;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "urls")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @SequenceGenerator(
            name = "seq_generator",
            sequenceName = "url_seq",
            initialValue = 100000000,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_generator"
    )
    @Column(name = "url_id")
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
}
