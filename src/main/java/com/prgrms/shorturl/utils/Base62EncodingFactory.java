package com.prgrms.shorturl.utils;

import com.prgrms.shorturl.domain.Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Base62EncodingFactory implements EncodingFactory {

    private final String hashString;
    private final String originalUrl;

    @Override
    public Url encode() {
        log.info("hashString: " + hashString);
            long hashId = Long.parseLong(hashString, 16);

            log.info("long type hashID: " + hashId);
            String[] base62Num = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

            StringBuilder sb = new StringBuilder();
            while (hashId > 0) {
                long mod = (long) hashId % 62;
                sb.append(base62Num[(int)mod]);
                hashId /= 62;
            }
            return new Url(hashString, originalUrl, sb.toString());
    }
}
