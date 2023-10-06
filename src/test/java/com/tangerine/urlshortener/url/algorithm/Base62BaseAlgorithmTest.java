package com.tangerine.urlshortener.url.algorithm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Base62BaseAlgorithmTest {

    @Test
    @DisplayName("Base62 알고리즘을 이용해 매핑 아이디를 단축 URL로 매핑한다.")
    void encode_Success() {
        // Given
        Long mappingId = 100L;
        Base62BaseAlgorithm algorithm = new Base62BaseAlgorithm();

        // When
        String encode = algorithm.encode(mappingId);

        // Then
        assertThat(algorithm.decode(encode)).isEqualTo(mappingId);
    }

}