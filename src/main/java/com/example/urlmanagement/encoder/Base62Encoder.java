package com.example.urlmanagement.encoder;

public class Base62Encoder implements ShortUrlEncoder {

    private static final int MAX_URL_LENGTH = 8;
    private static final int TOKENS_SIZE = 62;
    private static final char[] BASE62_TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    @Override
    public String createShortUrl(Long resource) {

        StringBuilder encodedUrl = new StringBuilder();

        while (resource > 0) {

            if (encodedUrl.length() == MAX_URL_LENGTH) {
                break;
            }

            int index = (int) (resource % TOKENS_SIZE);
            encodedUrl.append(BASE62_TOKENS[index]);
            resource /= TOKENS_SIZE;
        }

        return encodedUrl.toString();
    }
}
