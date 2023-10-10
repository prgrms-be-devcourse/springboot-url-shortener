package com.prgrms.shorturl.url.encoder;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomEncoder implements ShortUrlStrategy {

    private static final int URL_LENGTH_LIMIT = 8;
    private final char[] RANDOM_CHAR_TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private final Random random = new Random();

    @Override
    public String encodeOriginUrl(Long id) {
        StringBuilder encodedString = new StringBuilder(URL_LENGTH_LIMIT);

        for (int i = 0; i < URL_LENGTH_LIMIT; i++) {
            int randomNumber = random.nextInt(RANDOM_CHAR_TOKENS.length);
            int index = (int) ((randomNumber + id) % RANDOM_CHAR_TOKENS.length);

            encodedString.append(RANDOM_CHAR_TOKENS[index]);
        }

        return encodedString.toString();
    }
}
