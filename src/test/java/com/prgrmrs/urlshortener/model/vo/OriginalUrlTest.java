package com.prgrmrs.urlshortener.model.vo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class OriginalUrlTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = new LocalValidatorFactoryBean();
        ((LocalValidatorFactoryBean) validator).afterPropertiesSet();
    }

    @ParameterizedTest

    @MethodSource("provideSuccessOriginalUrl")
    void OriginalUrl_CreatedSuccessfully(String url) {
        // given
        OriginalUrl originalUrl = new OriginalUrl(url);

        // when
        Set<ConstraintViolation<OriginalUrl>> violations = validator.validate(originalUrl);

        // then
        Assertions.assertThat(violations).isEmpty();
        Assertions.assertThat(originalUrl.getUrl()).isEqualTo(url);
    }

    @ParameterizedTest

    @MethodSource("provideFailOriginalUrl")
    void OriginalUrl_FailCreation(String url) {
        // given
        OriginalUrl originalUrl = new OriginalUrl(url);

        // when
        Set<ConstraintViolation<OriginalUrl>> violations = validator.validate(originalUrl);

        // then
        Assertions.assertThat(violations).isNotEmpty();
    }

    static Stream<Arguments> provideSuccessOriginalUrl() {
        return Stream.of(
                Arguments.of("https://www.naver.com"),
                Arguments.of("http://www.naver.com"),
                Arguments.of("https://www.daum.net"),
                Arguments.of("https://www.google.com")
        );
    }

    static Stream<Arguments> provideFailOriginalUrl() {
        return java.util.stream.Stream.of(
                Arguments.of("htt://www.naver.com"),
                Arguments.of("httpss://www.naver.com"),
                Arguments.of("www.daum.net"),
                Arguments.of("https:/www.google.com")
        );
    }

}
