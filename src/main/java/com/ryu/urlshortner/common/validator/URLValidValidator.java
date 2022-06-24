package com.ryu.urlshortner.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class URLValidValidator implements ConstraintValidator<URLValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final String URL_REGEX = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";

        if (value == null) {
            return false;
        }

        Pattern pattern = Pattern.compile(URL_REGEX);
        return pattern.matcher(value).find();
    }
}
