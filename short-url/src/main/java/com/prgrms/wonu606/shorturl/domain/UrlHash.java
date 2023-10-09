package com.prgrms.wonu606.shorturl.domain;

import org.apache.commons.lang3.StringUtils;

public record UrlHash(
        String value
) {

    private static final int MAX_LENGTH = 8;

    public UrlHash {
        validate(value);
    }

    private void validate(String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("해시 값은 null이거나 빈 문자열일 수 없습니다.");
        }
        if (StringUtils.length(value) > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("단축 URL 해시는 %d자 이하여야 합니다. Current Length: %d", MAX_LENGTH, value.length())
            );
        }
    }
}
