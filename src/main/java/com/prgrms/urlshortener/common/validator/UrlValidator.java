package com.prgrms.urlshortener.common.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<UrlFormat, String> {

    private static final String URL_REGEX = "^(https?:\\/\\/)?([^.][\\da-z\\.-]+\\.[a-z\\.]{2,6}|[\\d\\.]+)([\\/:?=&#]{1}[\\da-z\\.-]+)*[\\/\\?]?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        return URL_PATTERN.matcher(url).matches();
    }

}
