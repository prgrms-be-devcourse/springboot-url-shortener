package com.urlshortener.shorturl.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SimpleShortUrlGenerator implements ShortUrlGenerator {
    private final Random random = new Random();

    @Value("${url.length}")
    private int length;

    @Value("${url.source}")
    private String charTable;

    @Override
    public String generate(String source) {
        StringBuilder result = new StringBuilder();
        int sourceLength = charTable.length();

        for (int i = 0; i < length; i++) {
            result.append(charTable.charAt(random.nextInt(sourceLength)));
        }

        return result.toString();
    }
}
