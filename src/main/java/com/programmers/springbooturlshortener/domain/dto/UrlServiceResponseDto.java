package com.programmers.springbooturlshortener.domain.dto;

import com.programmers.springbooturlshortener.domain.entity.LongUrl;
import com.programmers.springbooturlshortener.domain.entity.Url;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlServiceResponseDto {
    private LongUrl longUrl;
    private String shortUrl;
    private long hit;

    private UrlServiceResponseDto(LongUrl longUrl, String shortUrl, long hit) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.hit = hit;
    }

    public static UrlServiceResponseDto of(Url url) {
        return new UrlServiceResponseDto(
                LongUrl.from(url.getLongUrl()),
                url.getShortUrl(),
                url.getHit()
        );
    }
}