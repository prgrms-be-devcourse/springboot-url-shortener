package com.prgrms.shorturl.utils;

public class UrlFactory {
    public static String extractProtocol(String url) {
        if (url.startsWith("http://")) {
            return "http://";
        } else if (url.startsWith("https://")) {
            return "https://";
        } else {
            return "";
        }
    }

    public static String deleteProtocol(String url) {
        return url.replaceAll("^(http://|https://)", "");
    }
}
