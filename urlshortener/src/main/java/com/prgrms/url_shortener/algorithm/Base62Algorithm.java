package com.prgrms.url_shortener.algorithm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Base62Algorithm {

    private static final char[] BASE62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int BASE62_LENGTH = 62;

    public static String encode(Long value) {
        final StringBuilder sb = new StringBuilder();
        while (value > 0) {
            char encodedChar = BASE62_CHARS[(int) (value % BASE62_LENGTH)];
            sb.append(encodedChar);
            value /= BASE62_LENGTH;
        }
        return sb.toString();
    }

    public static Long decode(String value) {
        long result = 0;
        for (int i = 0; i < value.length(); i++) {
            result += new String(BASE62_CHARS).indexOf(value.charAt(i));
        }
        return result;
    }
}
