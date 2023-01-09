package com.example.urlshortenert2.ShorteningKeyMaker;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MyShorteningKeyMaker implements ShorteningKeyMaker {

    private static final char[] BASE_62_CHAR_ARRAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int BASE_62_LENGTH = BASE_62_CHAR_ARRAY.length;
    private static final int SHORT_URL_LENGTH = 8;
    private static final char PADDING_CHARACTER = '+';

    @Override
    public String makeShorteningKey(Long id) {
        StringBuilder stringBuilder = new StringBuilder();
        while (id > 0) {
            int idx = (int) (id % BASE_62_LENGTH);
            stringBuilder.append(BASE_62_CHAR_ARRAY[idx]);
            id /= BASE_62_LENGTH;
        }
        return padding(stringBuilder);
    }

    private String padding(StringBuilder stringBuilder) {
        if (stringBuilder.length() >= SHORT_URL_LENGTH) {
            return stringBuilder.toString();
        }
        while (stringBuilder.length() < SHORT_URL_LENGTH) {
            stringBuilder.append(PADDING_CHARACTER);
        }
        return stringBuilder.toString();
    }
}
