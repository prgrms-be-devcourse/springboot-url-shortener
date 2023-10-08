package com.tangerine.urlshortener.url.algorithm;

public class Base64BaseAlgorithm implements BaseAlgorithm {

    private static final char[] tokens = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();
    private static final int TOKEN_LENGTH_BOUND = 64;
    private static final int URL_LENGTH_BOUND = 8;
    private static final String PADDING_TOKEN = "A";

    @Override
    public String encode(Long encodeValue) {
        StringBuilder sb = new StringBuilder();
        Long value = encodeValue;
        while (value > 0) {
            sb.append(tokens[(int) (value % TOKEN_LENGTH_BOUND)]);
            value /= TOKEN_LENGTH_BOUND;
        }
        return padding(sb);
    }

    @Override
    public long decode(String decodeValue) {
        long value = 0L;
        long factor = 1L;
        for (int i = 0; i < URL_LENGTH_BOUND; i++) {
            value += String.valueOf(tokens).indexOf(decodeValue.charAt(i)) * factor;
            factor *= TOKEN_LENGTH_BOUND;
        }
        return value;
    }

    private String padding(StringBuilder sb) {
        int length = sb.length();
        while (length < URL_LENGTH_BOUND) {
            sb.append(PADDING_TOKEN);
            length++;
        }
        return sb.toString();
    }
}
