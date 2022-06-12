package com.prgrms.urlshortener.service.encoder;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder implements UrlEncoder {

    private static final int BASE62 = 62;
    private static final char[] BASE62_FORMAT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override
    public String encode(long id) {
        StringBuilder shortUrl = new StringBuilder();
        while (id > 0) {
            shortUrl.append(BASE62_FORMAT[((int)(id % BASE62))]);
            id /= BASE62;
        }
        return shortUrl.reverse().toString();
    }

    @Override
    public boolean resolve(String encodedType) {
        return EncodeType.BASE62.toString().equals(encodedType);
    }

}
