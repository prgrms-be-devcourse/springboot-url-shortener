package org.programmers.springbooturlshortener.service;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@UtilityClass
@Slf4j
public class UrlProtocolUtils {

    private static final String HTTP_PREFIX = "http://";

    private static final String PROTOCOL_REGEX = "(http|https|ftp|telnet|news|mms):\\/\\/.*";

    private static final String DEFAULT_PREFIX = HTTP_PREFIX;

    public static String addingHttpIfNoProtocolIn(String url) {
        if (isHavingProtocol(url)) {
            return url;
        }
        log.info("저장하려는 url \"{}\"에 프로토콜이 존재하지 않기에 기본 프로토콜 접두사 {}을 추가함", url, DEFAULT_PREFIX);
        return DEFAULT_PREFIX + url;
    }

    private static boolean isHavingProtocol(String url) {
        return Pattern.matches(PROTOCOL_REGEX, url);
    }
}