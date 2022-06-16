package com.prgrms.shortener.presentation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HttpUrlValidator.class)
public @interface HttpURL {

  String message() default "잘못된 형식의 URL 문자열입니다.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}

