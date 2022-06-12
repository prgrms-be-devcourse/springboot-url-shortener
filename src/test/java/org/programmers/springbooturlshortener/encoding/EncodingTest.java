package org.programmers.springbooturlshortener.encoding;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.programmers.springbooturlshortener.encoding.Encoding.*;

class EncodingTest {

    @ParameterizedTest
    @MethodSource("encodeParams")
    void encode(Long original, Encoding encoding, String result) {
        Assertions.assertThat(encoding.encode(original)).isEqualTo(result);
    }

    private static Stream<Arguments> encodeParams() {
        return Stream.of(
                arguments(1L,                   BASE62, "localhost:8080/11"),
                arguments(7952117461L,          BASE62, "localhost:8080/131HAg8"),
                arguments(56800235584L,         BASE62, "localhost:8080/10000001"),
                arguments(3521614606207L,       BASE62, "localhost:8080/1zzzzzzz")
        );
    }
}