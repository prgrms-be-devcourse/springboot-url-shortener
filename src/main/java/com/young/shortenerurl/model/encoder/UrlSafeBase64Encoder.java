package com.young.shortenerurl.model.encoder;

import org.springframework.stereotype.Component;

@Component
public class UrlSafeBase64Encoder extends Encoder{
    private static final int BASE64 = 64;
    private static final String BASE64_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";

    @Override
    public String encode(long index) {
        StringBuilder sb = new StringBuilder();
        while(index > 0) {
            sb.append(BASE64_CHAR.charAt((int) (index % BASE64)));
            index /= BASE64;
        }

        if (sb.length() > MAX_LENGTH){
            throw new IllegalArgumentException("변환된 url은 8자리를 넘어갈 수 없습니다.");
        }
        return sb.toString();
    }
}
