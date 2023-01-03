package com.programmers.springbooturlshortener.domain.algorithm;

import org.springframework.stereotype.Component;

@Component
public class Base62Algorithm {

    private static final char[] base62Char = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int BASE62_LENGTH = 62;
    private static final int SHORT_URL_LENGTH = 7;

    public String encode(Long id) {
        StringBuilder sb = new StringBuilder();

        while (id > 0) {
            sb.append(base62Char[(int) (id % BASE62_LENGTH)]);
            id /= BASE62_LENGTH;
        }

        return padding(sb.toString());
    }

    public Long decode(String shortUrl) {
        long result = 0;
        long power = 1;

        for (int i = shortUrl.length() - 1; i >= 0; i--) {
            result += new String(base62Char).indexOf(shortUrl.charAt(i)) * power;
            power *= BASE62_LENGTH;
        }

        return result;
    }

    private String padding(String shortUrl) {
        StringBuilder sb = new StringBuilder();

        if (shortUrl.length() < SHORT_URL_LENGTH) {
            int paddingCount = SHORT_URL_LENGTH - shortUrl.length();

            for (int i = 0; i < paddingCount; i++) {
                sb.append("A");
            }

            shortUrl = sb.append(shortUrl).toString();
        }

        return shortUrl;
    }
}
