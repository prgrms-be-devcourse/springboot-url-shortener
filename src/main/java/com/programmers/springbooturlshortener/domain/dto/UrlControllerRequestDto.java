package com.programmers.springbooturlshortener.domain.dto;

import com.programmers.springbooturlshortener.domain.entity.EncodeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UrlControllerRequestDto {
    private final String longUrl;
    private final EncodeType encodeType;
}
