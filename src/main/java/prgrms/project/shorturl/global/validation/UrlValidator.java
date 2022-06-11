package prgrms.project.shorturl.global.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class UrlValidator implements ConstraintValidator<UrlChecker, String> {

    private static final Pattern URL_PATTERN = Pattern.compile("^(https?:\\/\\/)?([\\da-z\\.-]+\\.[a-z\\.]{2,6}|[\\d\\.]+)([\\/:?=&#]{1}[\\da-z\\.-]+)*[\\/\\?]?$");
    private int min;
    private int max;

    @Override
    public void initialize(UrlChecker constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isNotBlank(value)
            && value.length() >= min
            && value.length() <= max
            && URL_PATTERN.matcher(value).matches();
    }
}
