package com.prgrms.urlshortener.domain.shortener.util;

import com.prgrms.urlshortener.global.error.exception.WrongFieldException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;


public class UrlValidator {
    public static void validateUrl(String url) {
        if(isBlank(url)) {
            throw new WrongFieldException("url", url, "blank is not allowed at url");
        }

        if(isNonValidUrlPattern(url)) {
            throw new WrongFieldException("url", url, "blank is not allowed at url");
        }
    }

    private static boolean isNonValidUrlPattern(String url) {
        Pattern pattern = Pattern.compile("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)");
        Matcher matcher = pattern.matcher(url);

        if(matcher.matches()) {
            return false;
        }else{
            return true;
        }
    }

    public static void validateRequestCount(long requestCount) {
        if(requestCount < 0) {
            throw new WrongFieldException("requestCount", requestCount, "requestCount >= 0");
        }
    }
}
