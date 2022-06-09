package com.prgrms.urlshortener.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class Base62Test {
    @DisplayName("base62 encoding과 decoding 정상 작동하는 확인하는 테스트")
    @Test
    void testEncodingAndDecoding() {
        long id = 1L;

        String encodedId = Base62.encoding(id);
        log.info("encodedId = {}", encodedId);

        long decodedId = Base62.decoding(encodedId);
        log.info("decodedId = {}", decodedId);

        assertThat(id).isEqualTo(decodedId);
    }
}