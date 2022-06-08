package com.taehan.urlshortener.util;

public class Converter {

    private Converter() {}

    public static String base62(long val) {
        final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        final int BASE62_COUNT = 62;

        final StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (val % BASE62_COUNT);
            sb.append(BASE62[i]);
            val /= BASE62_COUNT;
        } while (val > 0);
        return sb.toString();
    }
}
