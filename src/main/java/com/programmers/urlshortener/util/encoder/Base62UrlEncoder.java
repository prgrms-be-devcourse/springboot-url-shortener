package com.programmers.urlshortener.util.encoder;

public class Base62UrlEncoder implements UrlEncoder {
    private static final String BASE62_CHARACTER_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE62_CHARACTER_SET_SIZE = BASE62_CHARACTER_SET.length();
    private static final long UNSIGNED_INT_MASK = 0xffffffffL;

    @Override
    public String encode(String url) {
        long hash = url.hashCode() & UNSIGNED_INT_MASK;
        StringBuilder shortUrl = new StringBuilder();

        while (hash > 0) {
            shortUrl.insert(0, BASE62_CHARACTER_SET.charAt((int) (hash % BASE62_CHARACTER_SET_SIZE)));
            hash /= BASE62_CHARACTER_SET_SIZE;
        }

        return shortUrl.toString();
    }

    @Override
    public Algorithm getAlgorithm() {
        return Algorithm.BASE62;
    }
}

