package com.example.kdtspringbooturlshortener.urlinfo.domain;

import com.example.kdtspringbooturlshortener.global.common.util.Base62Converter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.kdtspringbooturlshortener.global.common.util.RegExp.URL_PATTERN;
import static java.util.Objects.requireNonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp = URL_PATTERN)
    private String originalUrl;

    private String shortUrl;

    private UrlInfo(String originalUrl, String shortUrl) {
        this.originalUrl = requireNonNull(originalUrl, "URL이 빈값입니다.");
        this.shortUrl = shortUrl;
    }

    public static UrlInfo create(String originalUrl, String shortUrl) {
        return new UrlInfo(originalUrl, shortUrl);
    }

    public void convertToShortUrl() {
        if (this.id == null) {
            throw new RuntimeException("URL 단축 실패");
        }

        this.shortUrl = Base62Converter.encode(id);
    }
}
