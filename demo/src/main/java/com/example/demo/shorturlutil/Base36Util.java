package com.example.demo.shorturlutil;

import org.springframework.stereotype.Component;

@Component
public class Base36Util {
    static final char[] BASE36 = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String encoding(int urlId) {
        final StringBuilder stringBuilder = new StringBuilder();
        while (urlId > 0) {
            int i = urlId % BASE36.length;
            stringBuilder.append(BASE36[i]);
            urlId /= BASE36.length;
        }
        return stringBuilder.toString();
    }

    public static int decoding(String shortUrl) {
        int resultNumber = 0;
        int power = 1;
        for (int i = 0; i < shortUrl.length(); i++) {
            int digit = new String(BASE36).indexOf(shortUrl.charAt(i));
            resultNumber += (digit * power);
            power *= BASE36.length;
        }
        return resultNumber;
    }
}
