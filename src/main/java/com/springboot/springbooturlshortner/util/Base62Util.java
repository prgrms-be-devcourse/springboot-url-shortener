package com.springboot.springbooturlshortner.util;

import org.springframework.stereotype.Component;

@Component
public class Base62Util {

    private final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int BASE62_CHARS_SIZE = 62;

    public String encoding(long urlId) {
        StringBuffer sb = new StringBuffer();
        int charactersLength = BASE62_CHARS.length();
        while (urlId > 0) {
            sb.append(BASE62_CHARS.charAt((int) (urlId % charactersLength)));
            urlId /= BASE62_CHARS_SIZE;
        }
        return sb.toString();
    }

    public long decoding(String uniqueKey) {
        long urlId = 0, power = 1;
        for (int i = 0; i < uniqueKey.length(); i++) {
            urlId += BASE62_CHARS.indexOf(uniqueKey.charAt(i)) * power;
            power *= BASE62_CHARS_SIZE;
        }
        return urlId;
    }

}
