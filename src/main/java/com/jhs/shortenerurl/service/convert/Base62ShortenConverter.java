package com.jhs.shortenerurl.service.convert;

public class Base62ShortenConverter implements ShortenConverter {

    private static final char[] BASE62_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    private static final int LENGTH = BASE62_CHARACTERS.length;

    @Override
    public String convert(Long id) {
        StringBuilder result = new StringBuilder();

        do {
            int remainder = (int) (id % LENGTH);
            result.append(BASE62_CHARACTERS[remainder]);
            id /= LENGTH;
        } while (id > 0);

        return result.toString();
    }

}
