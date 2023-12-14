package org.daehwi.shorturl.util;

import lombok.extern.slf4j.Slf4j;
import org.daehwi.shorturl.controller.dto.ResponseStatus;
import org.daehwi.shorturl.exception.CustomException;

import java.net.URL;

@Slf4j
public class UrlValidator {

    private static final String HTTP_SCHEME = "http://";
    private static final String HTTPS_SCHEME = "https://";

    public static String validateUrl(String urlString) {
        try {
            if (!isProtocolExist(urlString)) {
                urlString = HTTP_SCHEME + urlString;
            }
            new URL(urlString);
            return getCleanURL(urlString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ResponseStatus.INVALID_URL);
        }
    }

    private static boolean isProtocolExist(String urlString) {
        return urlString.startsWith(HTTP_SCHEME) || urlString.startsWith(HTTPS_SCHEME);
    }

    private static String getCleanURL(String urlString) {
        urlString = urlString
                .replace(HTTP_SCHEME, "")
                .replace(HTTPS_SCHEME, "");
        return urlString;
    }
}
