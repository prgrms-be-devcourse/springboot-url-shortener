package com.prgrmrs.urlshortener.util;

public class Base62 {

    static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String encode(Long sequence) {
        final StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (sequence % 62);
            sb.append(BASE62[i]);
            sequence /= 62;
        } while (sequence > 0);
        return sb.toString();
    }

}