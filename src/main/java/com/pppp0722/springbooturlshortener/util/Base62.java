package com.pppp0722.springbooturlshortener.util;

public class Base62 {

    private static final String CODES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String encoding(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            int remainder = (int) (id % 62);
            sb.append(CODES.charAt(remainder));
            id /= 62;
        }
        String shortId = sb.toString();

        return shortId;
    }

    public static long decoding(String shortId) {
        long id = 0;
        for (int i = 0; i < shortId.length(); i++) {
            id += Math.pow(62, i) * CODES.indexOf(shortId.charAt(i));
        }

        return id;
    }
}
