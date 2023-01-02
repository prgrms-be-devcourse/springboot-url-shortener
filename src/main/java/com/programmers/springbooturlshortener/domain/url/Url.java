package com.programmers.springbooturlshortener.domain.url;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name = "url",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk",
                        columnNames = {"algorithm", "origin_url"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Url {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "algorithm", length = 20)
    private String algorithm;

    @NotBlank
    @Column(name = "origin_url", nullable = false, length = 2000)
    private String originUrl;

    @Nullable
    @Column(name = "short_url", length = 7)
    private String shortUrl;

    @Column(name = "request_count")
    private Long requestCount;

    @Builder
    public Url(String algorithm, String originUrl, Long requestCount) {
        this.algorithm = algorithm;
        this.originUrl = originUrl;
        this.requestCount = requestCount;
    }

    public void increaseRequestCount() {
        this.requestCount++;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
