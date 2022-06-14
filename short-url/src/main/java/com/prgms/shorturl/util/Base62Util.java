package com.prgms.shorturl.util;

import org.springframework.stereotype.Component;

@Component
public class Base62Util implements BaseUtil{

    static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    @Override
    public String encoding(long id) {
        StringBuilder sb = new StringBuilder();
        do {
            int i  = (int) id % 62;
            sb.append(BASE62[i]);
            id /= 62;
        } while (id > 0);
        return sb.toString();
    }

    @Override
    public long decoding(String shortUrl) {
        long result = 0;
        long power = 1;
        for (int i = 0; i < shortUrl.length(); i++) {
            long digit = new String(BASE62).indexOf(shortUrl.charAt(i));
            result += digit * power;
            power *= 62;
        }
        return result;
    }

}
