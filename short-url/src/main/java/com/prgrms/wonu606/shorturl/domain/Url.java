package com.prgrms.wonu606.shorturl.domain;

import org.apache.commons.validator.routines.UrlValidator;

public record Url(String value) {

    public Url {
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(value)) {
            throw new IllegalArgumentException("잘못된 URL 주소입니다. Current URL: " + value);
        }
    }
}
