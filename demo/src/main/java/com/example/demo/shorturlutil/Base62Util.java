package com.example.demo.shorturlutil;

import org.springframework.stereotype.Component;

@Component
public class Base62Util {
    static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String encoding(int urlId) {
        final StringBuilder stringBuilder = new StringBuilder();
        while (urlId > 0) {
            int i = urlId % 62;
            stringBuilder.append(BASE62[i]);
            urlId /= 62;
        }
        return stringBuilder.toString();
    }

    public static int decoding(String shortUrl) {
        int resultNumber = 0;
        int power = 1;
        for (int i = 0; i < shortUrl.length(); i++) {
            int digit = new String(BASE62).indexOf(shortUrl.charAt(i));
            resultNumber += (digit * power);
            power *= 62;
        }
        return resultNumber;
    }
}
