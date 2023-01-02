package com.programmers.springbooturlshortener.domain.url.util;

import java.net.URL;
import java.net.URLConnection;

public class UrlValidation {

    public boolean validate(String originUrl) {
        try {
            if (hasNotProtocol(originUrl)) {
                originUrl = "https://" + originUrl;
            }

            URL url = new URL(originUrl);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean hasNotProtocol(String originUrl) {
        return !(originUrl.startsWith("https://") || originUrl.startsWith("http://"));
    }
}
