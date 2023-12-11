package com.programmers.springbooturlshortener.domain.dto;

import com.programmers.springbooturlshortener.domain.entity.EncodeType;
import com.programmers.springbooturlshortener.domain.entity.LongUrl;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlServiceRequestDto {
    private LongUrl longUrl;
    private String shortUrl;
    private EncodeType encodeType;

    @Builder
    private UrlServiceRequestDto(LongUrl longUrl, String shortUrl, EncodeType encodeType) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.encodeType = encodeType;
    }
}
