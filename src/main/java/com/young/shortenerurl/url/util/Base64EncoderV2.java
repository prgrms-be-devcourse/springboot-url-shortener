package com.young.shortenerurl.url.util;

import org.springframework.stereotype.Component;

@Component
public class Base64EncoderV2 extends Encoder {
    private static final int BASE62 = 64;
    private static final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~.";

    @Override
    public String encode(long index) {
        StringBuilder sb = new StringBuilder();
        while(index > 0) {
            sb.append(BASE62_CHAR.charAt((int) (index % BASE62)));
            index /= BASE62;
        }

        if (sb.length() > MAX_LENGTH){
            throw new IllegalArgumentException("변환된 url은 8자리를 넘어갈 수 없습니다.");
        }
        return sb.toString();
    }

}
