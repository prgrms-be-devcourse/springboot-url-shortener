package com.prgrms.urlshortener.service.encoder;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Base62EncoderTest {

    private UrlEncoder urlEncoder;

    @BeforeEach
    void setUp() {
        urlEncoder = new Base62Encoder();
    }

    @DisplayName("base62 방식으로 인코딩한다.")
    @ParameterizedTest
    @CsvSource(value = {"11157,2TX", "10,a"})
    void encode_base62(long id, String shortUrl) {
        // given
        // when
        String encodeUrl = urlEncoder.encode(id);

        // then
        assertThat(encodeUrl).isEqualTo(shortUrl);
    }

}
