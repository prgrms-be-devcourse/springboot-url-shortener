package com.ryu.urlshortner.common.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = URLValidValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface URLValid {

    String message() default "Url is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
