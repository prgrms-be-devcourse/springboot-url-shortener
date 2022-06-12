package com.prgrms.urlshortener.service.encoder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Base62Utils {

    private static final int BASE62 = 62;
    private static final BigInteger BASE62_DIGIT = BigInteger.valueOf(BASE62);
    private static final char[] BASE62_FORMAT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private Base62Utils() {
    }

    public static String encode(long id) {
        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            shortUrl.append(BASE62_FORMAT[((int)(id % BASE62))]);
            id /= BASE62;
        }
        return shortUrl.reverse().toString();
    }

    public static List<Character> encode(String str) {
        BigInteger hexValue = new BigInteger(str, 16).abs();
        List<Character> result = new ArrayList<>();

        while (hexValue.compareTo(BASE62_DIGIT) > 0) {
            int digit = hexValue.mod(BASE62_DIGIT).intValue();
            result.add(BASE62_FORMAT[digit]);
            hexValue = hexValue.divide(BASE62_DIGIT);
        }
        int digit = hexValue.mod(BASE62_DIGIT).intValue();
        result.add(BASE62_FORMAT[digit]);
        return result;
    }

}
