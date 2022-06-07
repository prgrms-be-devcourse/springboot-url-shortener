package prgms.marco.springbooturlshortener.service.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import prgms.marco.springbooturlshortener.service.utils.UrlEncoder;

class UrlEncoderTest {

    private UrlEncoder urlEncoder = new UrlEncoder();

    @Test
    @DisplayName("인코딩 테스트")
    void testEncoding()  {
        //given
        String index = "1234";

        //when
        String shortUrl = urlEncoder.urlEncoder(index);

        //then
        assertThat(index).isEqualTo(String.valueOf(urlEncoder.urlDecoder(shortUrl)));
    }
}