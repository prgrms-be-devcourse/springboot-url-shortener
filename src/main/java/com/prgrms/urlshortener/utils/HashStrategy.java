package com.prgrms.urlshortener.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component("hash")
public class HashStrategy implements URLShorteningStrategy {

    private final String BASE62;

    public HashStrategy(String base62) {
        BASE62 = base62;
    }

    public String shortenURL(String originalURL) {
        String salt = String.valueOf(System.currentTimeMillis());
        String hash = generateHash(originalURL + salt);

        return hash.substring(0, 7);
    }

    private String generateHash(String originalURL) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(originalURL.getBytes());
            return encodeBase62(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found.", e);
        }
    }

    private String encodeBase62(byte[] bytes) {
        StringBuilder encoded = new StringBuilder();
        for (byte b : bytes) {
            int val = b & 0xFF;
            encoded.append(BASE62.charAt(val % 62));
        }
        return encoded.toString();
    }
}