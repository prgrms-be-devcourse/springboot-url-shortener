package com.programmers.springbooturlshortener.domain.algorithm;

import org.springframework.stereotype.Component;

@Component
public class Base62Algorithm implements Algorithm {

    private static final char[] TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int BASE62_LENGTH = 62;
    private static final int SHORT_URL_LENGTH = 7;
    private static final String PADDING_TOKEN = "A";

    @Override
    public String encode(Long id) {

        StringBuilder encodedTokenAppender = new StringBuilder();

        while (id > 0) {
            encodedTokenAppender.append(TOKENS[(int)(id % BASE62_LENGTH)]);
            id /= BASE62_LENGTH;
        }

        return padding(encodedTokenAppender.toString());
    }

    @Override
    public Long decode(String shortUrl) {
        long result = 0;
        long power = 1;

        for (int i = shortUrl.length() - 1; i >= 0; i--) {
            result += new String(TOKENS).indexOf(shortUrl.charAt(i)) * power;
            power *= BASE62_LENGTH;
        }

        return result;
    }

    private String padding(String shortUrl) {
        StringBuilder paddingTokenAppender = new StringBuilder();

        if (shortUrl.length() < SHORT_URL_LENGTH) {
            int paddingCount = SHORT_URL_LENGTH - shortUrl.length();

            for (int i = 0; i < paddingCount; i++) {
                paddingTokenAppender.append(PADDING_TOKEN);
            }

            shortUrl = paddingTokenAppender.append(shortUrl).toString();
        }

        return shortUrl;
    }
}
