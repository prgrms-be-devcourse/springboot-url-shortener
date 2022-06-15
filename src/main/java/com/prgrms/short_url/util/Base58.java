package com.prgrms.short_url.util;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Base58 {

    private static final char[] BASE58 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghiljkmnopqrstuvwxyz0123456789".toCharArray();

    public String createRandomShortUrl() {
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        int length = random.nextInt(8) + 1;

        for(int i = 0; i < length; i++) {
            int nextIndex = random.nextInt(58);
            sb.append(BASE58[nextIndex]);
        }
        return sb.toString();
    }

}
