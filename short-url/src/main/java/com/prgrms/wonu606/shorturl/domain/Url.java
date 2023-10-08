package com.prgrms.wonu606.shorturl.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

public record Url(String value) {

    private static final UrlValidator URL_VALIDATOR = new UrlValidator();
    public static final int URL_MAX_LENGTH = 2_000;

    public Url {
        if (!URL_VALIDATOR.isValid(value)) {
            throw new IllegalArgumentException("잘못된 URL 주소입니다. Current URL: " + value);
        }

        if (StringUtils.length(value) > URL_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("URL의 길이는 %d를 넘어갈 수 없습니다 Current Length: %d", URL_MAX_LENGTH, value.length()));
        }
    }
}
