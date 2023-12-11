package com.programmers.springbooturlshortener.domain.infrastructure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncodeHelper {
    private static final String ALGORITHM = "SHA-256";

    protected static String encoding(String url, int hexLength, char[] tokens, int tokenLength) {
        StringBuilder result = new StringBuilder();
        try {
            long src = hashingURL(url.getBytes(), hexLength);
            do {
                result.insert(0, tokens[(int) (src % tokenLength)]);
                src /= tokenLength;
            } while (src > 0);
        } catch (NoSuchAlgorithmException ignored) {
        }
        return result.toString();
    }

    protected static String encoding(String url, int hexLength, char[] tokens, int tokenLength, String prefix) {
        return prefix + encoding(url, hexLength, tokens, tokenLength);
    }

    private static long hashingURL(byte[] bytes, int length) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        messageDigest.update(bytes);
        byte[] hashBytes = messageDigest.digest();
        return Long.parseLong(bytesToHex(hashBytes).substring(0, length), 16);
    }

    private static String bytesToHex(byte[] bytes) {
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
