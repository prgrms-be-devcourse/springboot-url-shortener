package prgrms.project.shorturl.global.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { UrlValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface UrlChecker {

    String message() default "{org.hibernate.validator.constraints.URL.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String protocol() default "";

    String host() default "";

    int port() default -1;
}
