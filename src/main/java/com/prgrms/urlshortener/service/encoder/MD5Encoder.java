package com.prgrms.urlshortener.service.encoder;

import java.util.Collections;
import java.util.List;

import org.springframework.util.DigestUtils;

class MD5Encoder implements UrlEncoder {

    public static final int ENCODE_LENGTH = 7;

    @Override
    public String encode(long id) {
        String hexValue = bytesToHex(encodeMD5(id));
        List<Character> encodeValue = Base62Utils.encode(hexValue);
        return extractRandom(encodeValue);
    }

    private byte[] encodeMD5(long id) {
        return DigestUtils.md5Digest(String.valueOf(id).getBytes());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            String string = String.format("%02x", b);
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    private String extractRandom(List<Character> encodeValue) {
        Collections.shuffle(encodeValue);
        List<Character> shortUrl = encodeValue.subList(0, ENCODE_LENGTH);
        return toString(shortUrl);
    }

    private String toString(List<Character> encodeValue) {
        StringBuilder sb = new StringBuilder();
        for (char c : encodeValue) {
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public boolean supports(String encodedType) {
        return EncodeType.MD5.toString().equals(encodedType);
    }

}
