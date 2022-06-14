package com.urlshortener.url.component;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component("lowerCaseShortUrlGenerator")
public class LowerCaseShortUrlGenerator implements ShortUrlGenerator {
    private static final Random random = new Random();
    private static final int totalLength = 8;
    private static final char[] charTable = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

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
