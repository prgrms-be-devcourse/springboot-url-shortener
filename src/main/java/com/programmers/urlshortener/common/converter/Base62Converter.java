package com.programmers.urlshortener.common.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Base62Converter {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();
    
    public static String encode(int value) {
        StringBuilder sb = new StringBuilder();

        while (value != 0) {
            sb.append(ALPHABET.charAt(value % BASE));
            value /= BASE;
        }

        return sb.reverse().toString();
    }
}
