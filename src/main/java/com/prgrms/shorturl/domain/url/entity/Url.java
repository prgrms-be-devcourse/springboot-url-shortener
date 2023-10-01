package com.prgrms.shorturl.domain.url.entity;

import com.prgrms.shorturl.algorithm.AlgorithmType;
import com.prgrms.shorturl.common.exception.BaseException;
import com.prgrms.shorturl.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.regex.Pattern;

@Entity
@Table(name = "urls")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    private static final String URL_REGEX = "^(https?:\\/\\/)?([^.][\\da-z\\.-]+\\.[a-z\\.]{2,6}|[\\d\\.]+)([\\/:?=&#]{1}[\\da-z\\.-]+)*[\\/\\?]?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "origin_url", length = 255, nullable = false)
    private String originUrl;

    @Column(name = "short_url", length = 8)
    private String shortUrl;

    @Column(name = "algoritnm_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AlgorithmType algorithmType;

    @Column(name = "request_count")
    private Long requestCount;

    @Builder
    public Url(
            String originUrl,
            AlgorithmType algorithmType
    ) {
        validateUrl(originUrl);

        this.originUrl = originUrl;
        this.algorithmType = algorithmType;
        requestCount = 0L;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void increaseRequestCount() {
        this.requestCount += 1;
    }

    private void validateUrl(String url) {
        if(!URL_PATTERN.matcher(url).matches()){
            throw new BaseException(ErrorCode.INVALID_URL_FORMAT);
        }
    }
}
