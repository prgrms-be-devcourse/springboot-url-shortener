package com.prgrms.short_url.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLConnection;

@Component
public class UrlValidation {

    private static final Logger log = LoggerFactory.getLogger(UrlValidation.class);
    private static final String httpPattern = "^(((https?|ftp|file)?)(\\:)?(\\/)?(\\/)?(www.)?)$?";

    public String httpValid(String url) {
        return url.matches(httpPattern) ? url : url.replaceAll(httpPattern, "https://www.");
    }

    public boolean connectValid(String url) {
        try {
           URL connectionUrl = new URL(url);
           URLConnection connection = connectionUrl.openConnection();
           connection.connect();
       } catch (Exception e) {
           log.info("입력한 URL로 접속 검사에 실패했습니다. {}", e.getMessage());
           return false;
       }
       return true;
    }



}
