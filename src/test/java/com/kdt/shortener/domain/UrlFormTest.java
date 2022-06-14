package com.kdt.shortener.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class UrlFormTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setup() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("URL form null value validation 테스트")
    void validateFormTest(){

        //given
        var form1 = new UrlForm();
        var form2 = new UrlForm();
        form2.setUrlValue("");
        var form3 = new UrlForm();
        form3.setUrlValue("       ");


        Set<ConstraintViolation<UrlForm>> violations = validator.validate(form1);
        assertThat(violations.isEmpty(), is(false));
        violations = validator.validate(form2);
        assertThat(violations.isEmpty(), is(false));
        violations = validator.validate(form3);
        assertThat(violations.isEmpty(), is(false));

    }

    @Test
    @DisplayName("URL form pateern validation 테스트")
    void test(){

        //given
        var form = new UrlForm();
        form.setUrlValue("file://innerFile.java");

        Set<ConstraintViolation<UrlForm>> violations = validator.validate(form);
        assertThat(violations.isEmpty(), is(false));
    }

}