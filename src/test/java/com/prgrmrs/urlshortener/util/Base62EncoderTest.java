package com.prgrmrs.urlshortener.util;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Base62EncoderTest {

    @ParameterizedTest
    @MethodSource("provideEncodeTestData")
    void Encode_SuccessfullyConverted(Long seq, String hash) {
        // given & when
        String encoded = Base62Encoder.encode(seq);

        // then
        Assertions.assertThat(encoded).isEqualTo(hash);
    }

    private static Stream<Arguments> provideEncodeTestData() {
        return Stream.of(
                Arguments.of(0L, "A"),
                Arguments.of(1L, "B"),
                Arguments.of(61L, "9"),
                Arguments.of(1234567890L, "BViHfU")
        );
    }

}