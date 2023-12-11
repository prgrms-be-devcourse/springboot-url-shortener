package com.devcourse.shorturl.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Base62Encoder implements ShortUrlEncoder{
    private static final int RADIX = 62;
    private static final String BASE62_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    @Override
    public String encode(Long id) {
        StringBuilder sb = new StringBuilder();
        while(id > 0) {
            log.info("id % radix = {}", (int) (id % RADIX));
            sb.append(BASE62_CHARACTERS.charAt((int) (id % RADIX)));
            id /= RADIX;
        }
        log.info("인코딩 결과 : {}", sb.toString());
        return sb.toString();
    }
}
