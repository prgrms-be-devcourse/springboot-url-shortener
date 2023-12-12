package com.prgrms.shorturl.domain;

import com.prgrms.shorturl.exception.EncodingAlgorithmException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodingFactory {
    public static String toBase62(String originalUrl) {
        try {
            Long hashId = getHashId(originalUrl);

            String[] base62Num = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

            StringBuilder sb = new StringBuilder();
            while (hashId > 0) {
                int mod = hashId.intValue() % 62;
                sb.append(base62Num[mod]);
                hashId /= 62;
            }
            return sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new EncodingAlgorithmException("인코딩 도중 오류가 발생했습니다.");
        }
    }
    private static Long getHashId(String originalUrl) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] sha256;
        md.update(originalUrl.getBytes("UTF-8"));
        return byteToHex(md.digest());
    }

    private static Long byteToHex(byte[] bytes) {
        String hexString = new BigInteger(bytes).toString(16);
        return Long.getLong(hexString);
    }
}
