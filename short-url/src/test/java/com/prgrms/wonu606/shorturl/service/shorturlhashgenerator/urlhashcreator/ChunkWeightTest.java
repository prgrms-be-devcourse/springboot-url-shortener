package com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.urlhashcreator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ChunkWeightTest {

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3, 4, 5
    })
    void positiveWeight_DoesNotThrowException(int positiveWeight) {
        // When & Then
        assertThatCode(() -> new ChunkWeight(positiveWeight))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {
            0, -1, -2, -3, -4, -5
    })
    void nonPositiveWeight_throwException(int nonPositiveWeight) {
        // When & Then
        assertThatThrownBy(() -> new ChunkWeight(nonPositiveWeight))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Weight 값은 양수여야 합니다. Current Weight: " + nonPositiveWeight);
    }
}
