package com.spring.shorturl.domain.data;

import org.springframework.stereotype.Component;

@Component
public class MyPageUrl {
    private final String myPage = "https://verygoodyonghoon.";

    public String shortUrlWithMyPageName(String encodedUrl) {
        return myPage + encodedUrl;
    }
}
