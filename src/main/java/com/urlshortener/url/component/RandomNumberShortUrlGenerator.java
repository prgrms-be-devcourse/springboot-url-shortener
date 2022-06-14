package com.urlshortener.url.component;

import org.springframework.stereotype.Component;

@Component("randomNumberShortUrlGenerator")
public class RandomNumberShortUrlGenerator implements ShortUrlGenerator {
    private static final int totalLength = 8;

    @Override
    public String generate(String source) {
        return String.valueOf(source.hashCode()).substring(0, totalLength);
    }
}
