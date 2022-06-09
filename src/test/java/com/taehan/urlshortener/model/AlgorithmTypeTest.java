package com.taehan.urlshortener.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AlgorithmTypeTest {

    @Test
    @DisplayName("Base62 컨버팅 알고리즘 테스트")
    void TestBase62Convert() {
        String convert = AlgorithmType.BASE62.convert(10324604314L);

        assertThat(convert.length()).isLessThan(8);

        convert = AlgorithmType.BASE62.convert(1);
        assertThat(convert).isEqualTo("B");

        convert = AlgorithmType.BASE62.convert(3521614606207L);
        assertThat(convert).isEqualTo("9999999");
    }


}