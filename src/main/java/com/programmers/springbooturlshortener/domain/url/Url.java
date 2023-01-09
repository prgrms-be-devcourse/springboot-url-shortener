package com.programmers.springbooturlshortener.domain.url;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private static final String HTTPS_PROTOCOL = "https://";
    private static final String HTTP_PROTOCOL = "http://";

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
    public Url(String algorithm, String originUrl) {
        this.algorithm = algorithm;
        this.originUrl = removeProtocolFromOriginUrl(originUrl);
        this.requestCount = 1L;
    }

    public void increaseRequestCount() {
        this.requestCount++;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    private String removeProtocolFromOriginUrl(String originUrl) {

        if (originUrl.startsWith(HTTPS_PROTOCOL)) {
            return originUrl.replace(HTTPS_PROTOCOL, "");
        }

        if (originUrl.startsWith(HTTP_PROTOCOL)) {
            return originUrl.replace(HTTP_PROTOCOL, "");
        }

        return originUrl;
    }
}
