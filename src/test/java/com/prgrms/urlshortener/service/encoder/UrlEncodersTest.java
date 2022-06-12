package com.prgrms.urlshortener.service.encoder;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlEncodersTest {

    @Autowired
    private UrlEncoders urlEncoders;

    private static Stream<Arguments> provideEncodeType() {
        return Stream.of(
            Arguments.of("BASE62", Base62Encoder.class),
            Arguments.of("MD5", MD5Encoder.class)
        );
    }

    @DisplayName("타입에 따라 알맞는 인코더를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideEncodeType")
    void getUrlEncoderByType(String encodeType, Class<UrlEncoder> urlEncoderClass) {
        // given
        // when
        UrlEncoder urlEncoder = urlEncoders.getUrlEncoderByType(encodeType);

        // then
        assertThat(urlEncoder).isInstanceOf(urlEncoderClass);
    }

    @DisplayName("지원하지 않는 타입이라면 예외를 발생한다.")
    @Test
    void getUrlEncoderByType_WrongType() {
        assertThatThrownBy(() -> urlEncoders.getUrlEncoderByType("SHA-256"))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
