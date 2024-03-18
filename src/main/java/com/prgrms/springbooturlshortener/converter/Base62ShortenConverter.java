package com.prgrms.springbooturlshortener.converter;

import org.springframework.stereotype.Component;

@Component
public class Base62ShortenConverter {
    private static final int BASE62 = 62;
    private static final String BASE62_CHAR = "aZbYc0XdWeV1fUgTh2SiRjQ3kPlOm4NnMoL5pKqJr6IsHtG7uFvEw8DxCyB9zA";

    public String encode(Long value) {
        final StringBuilder sb = new StringBuilder();
        do {
            sb.append(BASE62_CHAR.charAt((int) (value % BASE62)));
            value /= BASE62;
        } while (value > 0);
        return sb.toString();
    }
}

