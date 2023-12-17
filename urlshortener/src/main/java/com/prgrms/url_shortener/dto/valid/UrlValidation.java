package com.prgrms.url_shortener.dto.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.net.URL;
import java.net.URLConnection;

public class UrlValidation implements ConstraintValidator<UrlValid, String> {
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String HTTP_PROTOCOL = "http://";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            if (hasNotProtocol(value)) {
                value = HTTPS_PROTOCOL + value;
            }

            URL url = new URL(value);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean hasNotProtocol(String originUrl) {
        return !(originUrl.startsWith(HTTPS_PROTOCOL) || originUrl.startsWith(HTTP_PROTOCOL));
    }
}
