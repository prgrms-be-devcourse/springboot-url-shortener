package com.example.shorturl.util.algorithm;

import com.example.shorturl.domain.Algorithm;
import com.example.shorturl.exception.CustomException;
import com.example.shorturl.exception.ErrorCode;
import com.example.shorturl.repository.UrlRepository;
import com.example.shorturl.util.encoder.EncoderStrategy;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Sha256 implements EncoderStrategy {

    private static final String SALT = "salt-value";
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int OUTPUT_LENGTH = 7;
    private static final int MAX_RETRY_COUNT = 5;

    private final UrlRepository urlRepository;

    @Override
    public String encodeUrl(Long urlSequence) {
        try {
            String convertedString = String.valueOf(urlSequence);
            MessageDigest sha256 = MessageDigest.getInstance(HASH_ALGORITHM);
            return generateUniqueHash(convertedString, sha256);
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(ErrorCode.ALGORITHM_NOT_SUPPORTED);
        }
    }

    private String generateUniqueHash(String convertedString, MessageDigest sha256) {
        int retryCount = 0;
        String hash;
        do {
            hash = generateHash(convertedString + SALT, sha256);
            retryCount++;
            if (retryCount > MAX_RETRY_COUNT) {
                throw new CustomException(ErrorCode.ENCODING_FAILED);
            }
        } while (urlRepository.existsUrlByOriginUrlAndAlgorithm(hash, Algorithm.SHA_256));
        return hash;
    }

    private String generateHash(String input, MessageDigest sha256) {
        byte[] hashedBytes = sha256.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < Math.min(OUTPUT_LENGTH, hashedBytes.length); i++) {
            hexString.append(String.format("%02x", hashedBytes[i]));
        }
        return hexString.substring(0, OUTPUT_LENGTH);
    }
}
