package com.tangerine.urlshortener.url.algorithm;

public class Base64BaseAlgorithm implements BaseAlgorithm {

    private static final char[] tokens = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();
    private static final int TOKEN_LENGTH_BOUND = 64;
    private static final int URL_LENGTH_BOUND = 8;
    private static final String PADDING_TOKEN = "A";

    @Override
    public String encode(Long mappingId) {
        StringBuilder sb = new StringBuilder();
        while (mappingId > 0) {
            sb.append(tokens[(int) (mappingId % TOKEN_LENGTH_BOUND)]);
            mappingId /= TOKEN_LENGTH_BOUND;
        }
        return padding(sb);
    }

    @Override
    public long decode(String shortUrl) {
        long decodedId = 0L;
        long factor = 1L;
        for (int i = 0; i < URL_LENGTH_BOUND; i ++) {
            decodedId += String.valueOf(tokens).indexOf(shortUrl.charAt(i)) * factor;
            factor *= TOKEN_LENGTH_BOUND;
        }
        return decodedId;
    }

    private String padding(StringBuilder sb) {
        int length = sb.length();
        while (length < URL_LENGTH_BOUND) {
            sb.append(PADDING_TOKEN);
            length ++;
        }
        return sb.toString();
    }
}
