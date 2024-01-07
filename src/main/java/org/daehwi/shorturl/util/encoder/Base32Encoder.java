package org.daehwi.shorturl.util.encoder;


import java.math.BigInteger;

public class Base32Encoder implements Encoder {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final int BASE_LENGTH = ALPHABET.length();

    @Override
    public String encode(BigInteger bigInteger) {
        StringBuilder sb = new StringBuilder();
        while (bigInteger.compareTo(BigInteger.ZERO) > 0) {
            sb.insert(0, ALPHABET.charAt(bigInteger.mod(BigInteger.valueOf(BASE_LENGTH)).intValue()));
            bigInteger = bigInteger.divide(BigInteger.valueOf(BASE_LENGTH));
        }
        return sb.toString();
    }
}
