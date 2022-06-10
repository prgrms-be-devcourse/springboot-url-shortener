package com.taehan.urlshortener.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class AlgorithmTypeTest {

    @CsvSource({
            "1, B",
            "3521614606207, 9999999"
    })
    @ParameterizedTest
    @DisplayName("Base62 컨버팅 알고리즘 성공")
    void TestBase62Convert(Long index, String expect) {
        String actual = AlgorithmType.BASE62.convert(index);
        assertThat(actual).isEqualTo(expect);
    }
}