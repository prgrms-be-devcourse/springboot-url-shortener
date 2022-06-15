package com.prgrms.short_url.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlFormat {

    @Value("${server.port}")
    private String port;

    @Value("${server.host}")
    private String host;

    public String getShortUrl(String encodedUrl) {
        return "http://" + host + ":" + port + "/" + encodedUrl;
    }

}
