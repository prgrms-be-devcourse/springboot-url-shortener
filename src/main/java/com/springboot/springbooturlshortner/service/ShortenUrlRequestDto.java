package com.springboot.springbooturlshortner.service;

import com.springboot.springbooturlshortner.domain.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShortenUrlRequestDto {

    private String originUrl;

    public Url toUrlEntity() {
        return new Url(originUrl);
    }
}
