package org.programmers.springbooturlshortener.encoding;

import org.springframework.beans.factory.annotation.Value;

public enum Encoding {
    BASE62("1", new Base62Encoder());

    Encoding(String prefix, Encoder encoder) {
        this.typePrefix = prefix;
        this.encoder = encoder;
    }

    @Value("${url-shortener.base-url}")
    private static final String COMMON_PREFIX = "localhost:8080/";
    private final String typePrefix;
    private final Encoder encoder;

    public String encode(Long original) {
        return COMMON_PREFIX + typePrefix + encoder.encode(original);
    }
}