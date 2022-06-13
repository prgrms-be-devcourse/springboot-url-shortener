package com.urlshortener.shorturl.component;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SimpleShortUrlGenerator implements ShortUrlGenerator {
    private final Random random = new Random();
    private static final int totalLength = 8;
    private static final char[] charTable = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};

    @Override
    public String generate(String source) {
        StringBuilder result = new StringBuilder();
        int sourceLength = charTable.length;

        for (int i = 0; i < totalLength; i++) {
            result.append(charTable[random.nextInt(sourceLength)]);
        }

        return result.toString();
    }
}
