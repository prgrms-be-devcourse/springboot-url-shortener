package com.prgrms.shorturl.url.controller.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = HttpsUrlValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpUrl {
    String message() default "origin url은 \"http://\" 또는 \"https://\"로 시작해야 합니다.";

    Class[] groups() default {};

    Class[] payload() default {};

}
