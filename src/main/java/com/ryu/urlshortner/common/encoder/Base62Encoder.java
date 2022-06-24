package com.ryu.urlshortner.common.encoder;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder implements UrlEncoder {
    private final String BASE_62_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final char[] BASE_62_CHARS = BASE_62_STR.toCharArray();
    private final int BASE = BASE_62_CHARS.length;
    private final String URL_PREFIX = "http://localhost:8080/api/v1/urls/";

    @Override
    public String encode(long seq){
        StringBuilder encodedStr = new StringBuilder();

        if(seq == 0) {
            return String.valueOf(BASE_62_CHARS[0]);
        }

        while (seq > 0) {
            encodedStr.append(BASE_62_CHARS[(int) (seq % BASE)]);
            seq = seq / BASE;
        }

        return URL_PREFIX + encodedStr.reverse();
    }

    @Override
    public long decode(String shortUrl) {
        char[] urlChars = shortUrl.toCharArray();
        int length = urlChars.length;
        long decoded = 0L;
        long counter = 1L;

        for (int i = 0; i < length; i++) {
            decoded += BASE_62_STR.indexOf(urlChars[i]) * Math.pow(BASE, length - counter);
            counter++;
        }

        return decoded;
    }
}
