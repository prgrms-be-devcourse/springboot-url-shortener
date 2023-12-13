package org.daehwi.shorturl.util;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;

@Slf4j
public class UrlValidator {
    public static String validateUrl(String urlString) {
        try {
            new URL(urlString);
            return getCleanURL(urlString);
        } catch (Exception e) {
            log.error("URL validation failed. Url :{}", urlString);
            throw new IllegalArgumentException("Invalid URL");
        }
    }

    private static String getCleanURL(String urlString) {
        urlString = urlString
                .replace("http://", "")
                .replace("https://", "");
        return urlString;
    }
}
