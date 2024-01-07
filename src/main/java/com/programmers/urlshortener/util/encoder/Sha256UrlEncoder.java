package com.programmers.urlshortener.util.encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Sha256UrlEncoder implements UrlEncoder {
    private static final int HASH_MAX_LENGTH = 8;

    @Override
    public String encode(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            String uniqueUrl = url + System.currentTimeMillis();
            byte[] hash = digest.digest(uniqueUrl.getBytes());

            String encodedUrl = Base64.getUrlEncoder().encodeToString(hash);
            return encodedUrl.substring(0, Math.min(encodedUrl.length(), HASH_MAX_LENGTH));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 encoding failed", e);
        }
    }

    @Override
    public Algorithm getAlgorithm() {
        return Algorithm.SHA256;
    }
}
