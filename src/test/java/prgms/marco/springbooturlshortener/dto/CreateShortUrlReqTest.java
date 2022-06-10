package prgms.marco.springbooturlshortener.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CreateShortUrlReqTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("originalUrl 포멧 검증 성공")
    void testOriginUrlValidateSuccess() {
        //given
        CreateShortUrlReq createShortUrlReq = new CreateShortUrlReq("http://www.google.com");

        //when
        Set<ConstraintViolation<CreateShortUrlReq>> validate =
            validator.validate(createShortUrlReq);

        //then
        assertThat(validate).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "www.google.com"})
    @DisplayName("이메일 검증 실패")
    void testOriginUrlValidateFail() {
        //given
        CreateShortUrlReq createShortUrlReq = new CreateShortUrlReq("");

        //when
        Set<ConstraintViolation<CreateShortUrlReq>> validate =
            validator.validate(createShortUrlReq);

        //then
        assertThat(validate).hasSize(1);
    }
}