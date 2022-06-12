package com.kdt.shortener.utils;

import org.springframework.stereotype.Component;

@Component
public class Base62Utils {

    static final int RADIX = 62;

    static final String CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String encoding(long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(CODE.charAt((int) id % RADIX));
            id /= RADIX;
        }
        return sb.toString();
    }

    public long decoding(String shortUrl) {
        long result = 0;
        long power = 1;
        for (int i = 0; i < shortUrl.length(); i++) {
            result += CODE.indexOf(shortUrl.charAt(i)) * power;
            power *= RADIX;
        }
        return result;
    }
}
