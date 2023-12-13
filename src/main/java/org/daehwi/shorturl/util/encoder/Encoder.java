package org.daehwi.shorturl.util.encoder;

import java.math.BigInteger;
import java.security.MessageDigest;

import static java.nio.charset.StandardCharsets.UTF_8;

public interface Encoder {

    String encode(BigInteger bigInteger);

    default BigInteger sha256Hash(String url, int hashSize) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(url.getBytes(UTF_8));
            BigInteger bigInteger = new BigInteger(1, hash);
            // 16진수로 변환 후, hashSize 만큼만 잘라서 반환 (앞에서부터)
            String hashString = bigInteger.toString(16);
            String shortHash = hashString.substring(0, hashSize);
            return new BigInteger(shortHash, 16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
