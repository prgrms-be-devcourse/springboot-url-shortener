package kdt.shorturl.grobal.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = {UrlValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlValid {

    String message() default "Invalid URL";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
