package com.prgrms.shortener.presentation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HttpUrlValidator implements ConstraintValidator<HttpURL, String> {

  @Override
  public void initialize(HttpURL constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value.matches("^https?://(www\\.)?[-a-zA-Z\\d@:%._+~#=]{1,256}\\.[a-zA-Z\\d()]{1,6}\\b([-a-zA-Z\\d()@:%_+.~#?&/=]*)$");
  }
}
