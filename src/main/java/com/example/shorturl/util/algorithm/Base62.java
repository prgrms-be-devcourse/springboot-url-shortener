package com.example.shorturl.util.algorithm;

import com.example.shorturl.util.encoder.EncoderStrategy;
import org.springframework.stereotype.Component;

@Component
public class Base62 implements EncoderStrategy {

    private static final int RADIX_BASE = 62;
    private static final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Override
    public String encodeUrl(Long urlSequence) {
        return generateEncodedUrl(urlSequence);
    }

    private String generateEncodedUrl(Long sequence) {
        StringBuilder encodedUrl = new StringBuilder();
        while(sequence > 0) {
            int remainder = (int) (sequence % RADIX_BASE);
            encodedUrl.insert(0, BASE62_CHAR.charAt(remainder));
            sequence /= RADIX_BASE;
        }
        return encodedUrl.toString();
    }
}
