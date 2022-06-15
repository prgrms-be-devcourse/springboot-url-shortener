package com.su.urlshortener.url.service.shortener;

import org.thymeleaf.util.StringUtils;

import java.util.Random;

public enum ShorteningAlgorithm implements UrlShortener {
    SEQUENCE {
        public String makeShorteningKey(String originUrl) {
            return String.format("%08x", ++sequence);
        }
    },

    RANDOM {
        public String makeShorteningKey(String originUrl) {
            String generated = "";
            for (int i = 0; i < 8; i++) {
                int wordsIdx = random.nextInt(words.length());
                generated = StringUtils.append(generated, String.valueOf(words.charAt(wordsIdx)));
            }
            return generated;
        }
    };

    private static Random random = new Random();
    private static int sequence = 16;
    private static String words = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
}
