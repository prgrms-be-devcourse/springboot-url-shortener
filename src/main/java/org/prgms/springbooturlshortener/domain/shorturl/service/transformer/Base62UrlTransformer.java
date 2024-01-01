package org.prgms.springbooturlshortener.domain.shorturl.service.transformer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("BASE62")
public class Base62UrlTransformer implements UrlTransformer{
    private static final String BASE62_TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final short NUM_OF_URL = 6;
    private static final int DIVIDE_NUM = 62;

    public String transform(int number) {
        StringBuilder sb = new StringBuilder();

        for (short i = 0; i < NUM_OF_URL; i++) {
            char token = BASE62_TOKENS.charAt(number % DIVIDE_NUM);

            sb.append(token);
            number /= DIVIDE_NUM;
        }

        return sb.toString();
    }
}
