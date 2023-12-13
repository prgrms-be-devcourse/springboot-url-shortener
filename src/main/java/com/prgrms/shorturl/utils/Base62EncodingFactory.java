package com.prgrms.shorturl.utils;

import com.prgrms.shorturl.domain.ShortUrl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Base62EncodingFactory implements EncodingFactory {

    private final String hashString;
    private final String originalUrl;

    @Override
    public ShortUrl encode() {
            Long hashId = Long.getLong(hashString);

            String[] base62Num = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

            StringBuilder sb = new StringBuilder();
            while (hashId > 0) {
                int mod = hashId.intValue() % 62;
                sb.append(base62Num[mod]);
                hashId /= 62;
            }
            return new ShortUrl(hashString, originalUrl, sb.toString());
    }
}
