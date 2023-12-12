package com.pgms.shorturlcoredomain.util;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Base62Converter implements AlgorithmConverter{

    private static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public String encode(Long v){
        StringBuilder sb = new StringBuilder();
        do {
            long i = v % 62;
            sb.append(BASE62[(int) i]);
            v /= 62;
        } while (v > 0);
        return sb.toString();
    }
}
