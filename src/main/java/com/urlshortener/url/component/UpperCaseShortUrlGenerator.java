package com.urlshortener.url.component;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component("upperCaseShortUrlGenerator")
public class UpperCaseShortUrlGenerator implements ShortUrlGenerator {
    private static final Random random = new Random();
    private static final int totalLength = 8;
    private static final char[] charTable = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

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
