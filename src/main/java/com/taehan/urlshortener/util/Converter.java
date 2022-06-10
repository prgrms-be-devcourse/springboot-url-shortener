package com.taehan.urlshortener.util;

public class Converter {
    private static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int BASE62_COUNT = 62;
    private static final int FINISH_NUM = 0;

    private Converter() {}

    public static String base62(long index) {
        final StringBuilder sb = new StringBuilder();

        do {
            int i = (int) (index % BASE62_COUNT);
            sb.append(BASE62[i]);
            index /= BASE62_COUNT;
        } while (index > FINISH_NUM);
        return sb.toString();
    }
}
