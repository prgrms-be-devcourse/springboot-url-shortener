package com.programmers.springbooturlshortener.domain.infrastructure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.programmers.springbooturlshortener.domain.infrastructure.utils.EncodeHelper.encoding;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Base58 {
    private static final char[] TOKENS = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2', '3',
            '4', '5', '6', '7', '8', '9'
    };

    private static final String PREFIX = "-";
    private static final int HEX_LENGTH = 7;
    private static final int TOKEN_LENGTH = TOKENS.length;

    public static String encode(String url) {
        return encoding(url, HEX_LENGTH, TOKENS, TOKEN_LENGTH, PREFIX);
    }

}
