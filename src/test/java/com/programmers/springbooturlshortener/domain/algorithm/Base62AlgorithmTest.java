package com.programmers.springbooturlshortener.domain.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Base62AlgorithmTest {

    Base62Algorithm algorithm = new Base62Algorithm();

    @Test
    @DisplayName("입력받은 id를 정상적으로 Base62로 인코딩한다.")
    void encodeSuccess() {

        // given
        List<Long> ids = List.of(1L, 2L, 3L);
        List<String> answers = List.of("AAAAAAB", "AAAAAAC", "AAAAAAD");

        // when, then
        for (int i = 0; i < ids.size(); i++) {
            String result = algorithm.encode(ids.get(i));
            assertThat(result).isEqualTo(answers.get(i));
        }
    }

    @Test
    @DisplayName("Base62로 인코딩된 값을 원래 id로 정상적으로 디코딩한다.")
    void decodeSuccess() {

        // given
        List<String> decodedValues = List.of("AAAAAAB", "AAAAAAC", "AAAAAAD");
        List<Long> answers = List.of(1L, 2L, 3L);

        // when, then
        for (int i = 0; i < decodedValues.size(); i++) {
            Long result = algorithm.decode(decodedValues.get(i));
            assertThat(result).isEqualTo(answers.get(i));
        }
    }
}