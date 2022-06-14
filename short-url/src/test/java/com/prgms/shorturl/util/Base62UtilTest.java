package com.prgms.shorturl.util;

import static org.assertj.core.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class Base62UtilTest {

    @Autowired
    private Base62Util base62Util;

    @Test
    @DisplayName("id를 encoding하여 shortUrl로 반환한다. ")
    void encodingTest() {
        // Given
        Long id = 100L;

        // When
        String encoding = base62Util.encoding(id);

        // Then
        log.info("{}", encoding);
        assertThat(encoding).isEqualTo("mB");
    }

    @Test
    @DisplayName("shortUrl를 decoding하여 id를 반환한다.")
    void decodingTest() {
        // Given
        String shortUrl = "mB";

        // When
        long decoding = base62Util.decoding(shortUrl);

        // Then
        log.info("{}", decoding);
        assertThat(decoding).isEqualTo(100L);
    }

}