package com.prgrms.urlshortener.service.encoder;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MD5EncoderTest {

    private UrlEncoder urlEncoder;

    @BeforeEach
    void setUp() {
        urlEncoder = new MD5Encoder();
    }

    @DisplayName("MD5방식으로 인코딩한다.")
    @Test
    void encode_base64() {
        // given
        // when
        String encodeUrl = urlEncoder.encode(1);

        // then
        assertThat(encodeUrl).hasSize(7);
    }

}
