package org.prgms.springbooturlshortener.domain.shorturl.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("BASE62")
public class Base62UrlTransformer implements UrlTransformer{
    private static final String BASE62_TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final short numOfUrl = 6;

    public String generateUrl(int number) {
        StringBuilder sb = new StringBuilder();

        for (short i = 0; i < numOfUrl; i++) {
            char token = BASE62_TOKENS.charAt(number % 62);

            sb.append(token);
            number /= 62;
        }

        return sb.toString();
    }
}
