package com.kdt.shortener.utils;

import org.apache.logging.log4j.util.Chars;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Base62 {

    final int RADIX = 62;
    final char[] CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public String encoding(long id) {
        StringBuffer sb = new StringBuffer();
        while (id > 0) {
            sb.append(CODE[(int) id % RADIX]);
            id /= RADIX;
        }
        return sb.toString();
    }

    public long decoding(String shortUrl) {
        long result = 0;
        long power = 1;
        for (int i = 0; i < shortUrl.length(); i++) {
            result += Arrays.asList(CODE).indexOf(shortUrl.charAt(i)) * power;
            power *= RADIX;
        }
        return result;
    }
}
