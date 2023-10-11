package com.prgrmrs.urlshortener.util;

public class Base62Encoder {

    static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    private Base62Encoder() {
        // on purpose
    }

    public static String encode(Long sequence) {
        final StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (sequence % 62);
            sb.insert(0, BASE62[i]);  // Prepend the character
            sequence /= 62;
        } while (sequence > 0);
        return sb.toString();
    }

}