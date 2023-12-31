package com.example.shorturl.util.formatter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlFormatter {

    private static final String LOCAL_DOMAIN = "http://localhost:8080/";

    private UrlFormatter() {
    }

    public static String format(String encodedUrl) {
        String shortUrl = LOCAL_DOMAIN + encodedUrl;
        log.info("인코딩 된 ShortUrl = {}", shortUrl);
        return shortUrl;
    }
}
