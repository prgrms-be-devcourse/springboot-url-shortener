package com.prgrms.shorturl.url.encoder;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder implements ShortUrlStrategy {

    private static final int URL_LENGTH_LIMIT = 8;
    private static final int TOKENS_SIZE = 62;
    private final char[] TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    @Override
    public String encodeOriginUrl(Long id) {
        StringBuilder encodedString = new StringBuilder();
        while (id > 0) {
            if (isSameUrlLimit(encodedString)) {
                break;
            }

            encodedString.append(TOKENS[(int) (id % TOKENS_SIZE)]);
            id /= TOKENS_SIZE;
        }

        return encodedString.toString();
    }

    private boolean isSameUrlLimit(StringBuilder url) {
        return url.length() > URL_LENGTH_LIMIT;
    }

}
