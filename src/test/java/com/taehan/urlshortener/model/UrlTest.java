package com.taehan.urlshortener.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UrlTest {

    private static Validator validator;
    private static Logger log = LoggerFactory.getLogger(UrlTest.class);

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("count 증가 테스트")
    void testAddCount() {
        Url url = new Url("http://www.naver.com", "http://taehan/kkdsac2", AlgorithmType.CUSTOM);
        url.addCount();

        assertThat(url.getCount()).isEqualTo(1);
    }

    @DisplayName("url 유효성 검사 성공 테스트")
    @CsvSource({"www.naver.com, 1234",
            "naver.com, 12345678",
            "bit.ly, aa1ZZd"
    })
    @ParameterizedTest
    public void testValidator_success(String url, String shortUrl) {
        Url newUrl = new Url(url, shortUrl, AlgorithmType.CUSTOM);
        Set<ConstraintViolation<Url>> validate = validator.validate(newUrl);

        assertThat(validate).isEmpty();
    }

    @Test
    @DisplayName("url 필드는 프로토콜을 제외한 url 패턴만 등록될 수 있다.")
    public void testValidator_url() {
        Url url = new Url("http://www.naver.com", "1234", AlgorithmType.CUSTOM);
        Set<ConstraintViolation<Url>> validate = validator.validate(url);

        validate.stream()
                .forEach(constraintViolation -> log.info(constraintViolation.getMessage()));
        assertThat(validate).hasSize(1);
    }

    @Test
    @DisplayName("shortUrl은 대, 소문자 숫자의 조합이여야 한다")
    public void testValidator_shortUrl_regex() {
        Url url = new Url("www.naver.com", "a11b!", AlgorithmType.CUSTOM);
        Set<ConstraintViolation<Url>> validate = validator.validate(url);

        validate.stream()
                .forEach(constraintViolation -> log.info(constraintViolation.getMessage()));
        assertThat(validate).hasSize(1);
    }

    @Test
    @DisplayName("shortUrl은 8글자를 넘을 수 없다")
    public void testValidator_shortUrl_max_length() {
        Url url = new Url("www.naver.com", "123456789", AlgorithmType.CUSTOM);
        Set<ConstraintViolation<Url>> validate = validator.validate(url);

        validate.stream()
                .forEach(constraintViolation -> log.info(constraintViolation.getMessage()));
        assertThat(validate).hasSize(1);
    }

}