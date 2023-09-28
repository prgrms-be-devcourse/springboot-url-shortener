package com.prgrms.urlshortener.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomStrategy implements URLShorteningStrategy {

    private final String BASE62;
    private static final int SHORT_URL_LENGTH = 7;

    public RandomStrategy(String base62) {
        BASE62 = base62;
    }

    public String shortenURL(String originalURL) {
        return generateRandomString(SHORT_URL_LENGTH);
    }

    private String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder shortenedURL = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(BASE62.length());
            shortenedURL.append(BASE62.charAt(index));
        }

        return shortenedURL.toString();
    }
}