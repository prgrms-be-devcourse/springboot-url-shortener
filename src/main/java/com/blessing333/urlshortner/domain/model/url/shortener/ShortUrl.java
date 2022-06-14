package com.blessing333.urlshortner.domain.model.url.shortener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ShortUrl {
    private final String key;
    private final String newUrl;
}
