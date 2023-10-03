package com.programmers.urlshortener.common.validator;

import java.util.regex.Pattern;

import com.programmers.urlshortener.common.exception.ExceptionRule;
import com.programmers.urlshortener.common.exception.UrlException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlValidator {

    private static final Pattern URL_PATTERN = Pattern.compile(
        "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");

    public static void validateUrlPattern(String url) {
        if (!URL_PATTERN.matcher(url).matches()) {
            throw new UrlException(ExceptionRule.BAD_REQUEST);
        }
    }
}
