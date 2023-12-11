package com.programmers.springbooturlshortener.domain.infrastructure.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.programmers.springbooturlshortener.domain.infrastructure.utils.EncodeHelper.encoding;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Base62 {
    private static final char[] TOKENS = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    private static final int TOKEN_LENGTH = TOKENS.length;
    private static final int HEX_LENGTH = 10;

    public static String encode(String url) {
        return encoding(url, HEX_LENGTH, TOKENS, TOKEN_LENGTH);
    }

}
