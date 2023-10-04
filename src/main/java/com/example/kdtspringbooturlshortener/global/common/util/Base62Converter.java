package com.example.kdtspringbooturlshortener.global.common.util;

public class Base62Converter {
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();

    public static String encode(long value) {
        StringBuilder sb = new StringBuilder();

        while (value != 0) {
            int index = (int) (value % BASE);
            sb.append(ALPHABET.charAt(index));
            value /= BASE;
        }

        return sb.reverse().toString();
    }
}
