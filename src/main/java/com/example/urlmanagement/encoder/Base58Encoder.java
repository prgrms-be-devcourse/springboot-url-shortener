package com.example.urlmanagement.encoder;

public class Base58Encoder implements ShortUrlEncoder {

    private static final int MAX_URL_LENGTH = 8;
    private static final char[] BASE58_TOKENS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz123456789".toCharArray();
    private static final int TOKENS_SIZE = BASE58_TOKENS.length;

    @Override
    public String createShortUrl(Long resource) {

        StringBuilder encodedUrl = new StringBuilder();

        while (resource > 0) {

            if (encodedUrl.length() == MAX_URL_LENGTH) {
                break;
            }

            int index = (int) (resource % TOKENS_SIZE);
            encodedUrl.append(BASE58_TOKENS[index]);
            resource /= TOKENS_SIZE;
        }

        return encodedUrl.toString();
    }
}
