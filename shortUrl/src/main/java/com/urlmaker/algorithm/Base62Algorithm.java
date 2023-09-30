package com.urlmaker.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@Slf4j
public class Base62Algorithm implements Algorithm{

    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MAX_URL_LENGTH = 8;
    private static final int MAX_BASE62_LENGTH = 62;
    private static final String PADDING_VALUE = "A";

    @Override
    public String encode(Long urlId) {
        StringBuilder stringBuilder = new StringBuilder();

        while(urlId > 0){
            stringBuilder.append(BASE62.charAt((int) (urlId % MAX_BASE62_LENGTH)));
            urlId /= MAX_BASE62_LENGTH;
        }

        return padding(stringBuilder.toString());
    }

    @Override
    public Long decode(String shortenUrl) {
        long sum = 0;
        long power = 1;

        for (int i = 0; i < shortenUrl.length(); i++) {
            sum += BASE62.indexOf(shortenUrl.charAt(i)) * power;
            power *= MAX_BASE62_LENGTH;
        }

        return sum;
    }

    /**
     * 8자리 수보다 길이가 짧게 변환된 경우
     */
    private String padding(String shortenUrl){
        if(shortenUrl.length() < MAX_URL_LENGTH){
            StringBuilder updateShortenUrl = new StringBuilder();
            updateShortenUrl.append(shortenUrl);

            int paddingCount = MAX_URL_LENGTH - shortenUrl.length();
            IntStream.range(0, paddingCount)
                    .mapToObj(i -> PADDING_VALUE)
                    .forEach(updateShortenUrl::append);

            shortenUrl = updateShortenUrl.toString();
        }

        return shortenUrl;
    }
}
