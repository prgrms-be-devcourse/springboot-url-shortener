package com.prgrms.shorturl.algorithm;

import org.springframework.stereotype.Component;

@Component
public class Base62 implements Algorithm {

    private static final String TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int BASE62_LENGTH = 62;
    private static final int SHORTEN_URL_LENGTH = 8;
    private static final String PADDING_TOKEN = "A";

    /**
     * db에서 자동 생성된 id 값을 인코딩
     */
    @Override
    public String encode(Long id) {
        StringBuilder encodedTokenBuilder = new StringBuilder();

        if (id > 0) {
            encodedTokenBuilder.append(TOKENS.charAt((int)(id % BASE62_LENGTH)));
            id /= BASE62_LENGTH;
        }

        return padding(encodedTokenBuilder.toString());
    }

    @Override
    public Long decode(String shortUrl) {
        long originId = 0;
        long power = 1;

        for (int i = 0; i < shortUrl.length(); i++) {
            originId += TOKENS.indexOf(shortUrl.charAt(i)) * power;
            power *= BASE62_LENGTH;
        }

        return originId;
    }

    /**
     * 인코딩 된 url 길이가 7 미만일 경우 -> 길이 통일화
     */
    public String padding(String shortUrl) {
        StringBuilder unificateTokenBuilder = new StringBuilder();

        if (shortUrl.length() < SHORTEN_URL_LENGTH) {
            int padding_count = SHORTEN_URL_LENGTH - shortUrl.length();

            for (int i = 0; i < padding_count; i++) {
                unificateTokenBuilder.append(PADDING_TOKEN);
            }

            return unificateTokenBuilder.append(shortUrl).toString();
        }

        return shortUrl;
    }
}
