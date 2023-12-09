package com.programmers.springbooturlshortener.domain.infrastructure.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Base62 implements Encoder {
    private static final char[] TOKENS = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    private static final int TOKEN_LENGTH = TOKENS.length;
    private static final String ALGORITHM = "SHA-256";

    @Override
    public String encode(String url) {
        StringBuilder result = new StringBuilder();
        try {
            long src = hashingURL(url.getBytes());
            do {
                result.insert(0, TOKENS[(int) (src % TOKEN_LENGTH)]);
                src /= TOKEN_LENGTH;
            } while (src > 0);

        } catch (NoSuchAlgorithmException ignored) {
        }
        return result.toString();
    }

    private long hashingURL(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        messageDigest.update(bytes);
        byte[] hashBytes = messageDigest.digest();
        return Long.parseLong(bytesToHex(hashBytes).substring(0, 10), 16);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        StringBuilder sb = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            sb.append(hexDigits[(b >> 4) & 0xf]).append(hexDigits[b & 0xf]);
        }
        return sb.toString();
    }
}
