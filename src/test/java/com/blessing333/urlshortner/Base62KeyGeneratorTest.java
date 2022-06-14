package com.blessing333.urlshortner;

import com.blessing333.urlshortner.domain.model.url.key.Base62KeyGenerator;
import com.blessing333.urlshortner.domain.model.url.key.KeyGenerateFailException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class Base62KeyGeneratorTest {
    private final static Long MAXIMUM_RANGE = 3521614606207L;
    private final Base62KeyGenerator keyGenerator = new Base62KeyGenerator();

    @DisplayName("0 ~ 3521614606207 범위의 숫자를 base62로 인코딩 할 수 있어야 한다.")
    @Test
    void generateKeyTest() {
        String s = keyGenerator.generateKey(MAXIMUM_RANGE);
        Assertions.assertThat(s).isEqualTo("ZZZZZZZ");
    }

    @DisplayName("인코딩 가능 범위를 초과하면 KeyGenerateFailException 발생시킨다.")
    @Test
    void generateOutRangeKey() {
        assertThrows(KeyGenerateFailException.class, () -> keyGenerator.generateKey(MAXIMUM_RANGE + 1));
    }
}