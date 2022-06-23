package com.ryu.urlshortner.common.encoder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Base62EncoderTest {

    final Base62Encoder encoder = new Base62Encoder();

    @Test
    @DisplayName("전달받은 62 이상의 seq를 base62로 인코딩하여 축약된 Url를 생성한다.")
    void encode_more_than_62(){
        //given
        long seq = 10000000L;

        //when
        String result = encoder.encode(seq);

        //then
        System.out.println(result);
        assertThat(result).isEqualTo("http://localhost:8080/api/v1/urls/P7Cu");
    }

    @Test
    @DisplayName("전달받은 62 이하의 seq를 base62로 인코딩하여 축약된 Url를 생성한다.")
    void encode_less_than_62(){
        //given
        long seq = 10L;

        //when
        String resultUrl = encoder.encode(seq);

        //then
        assertThat(resultUrl).isEqualTo("http://localhost:8080/api/v1/urls/k");
    }

    @Test
    @DisplayName("전달받은 shortUrl를 base62로 디코딩하여 long 타입의 seq를 생성한다.")
    void decode(){
        //given
        String shortUrl = "P7Cu";

        //when
        long resultSeq = encoder.decode(shortUrl);

        //then
        assertThat(resultSeq).isEqualTo(10000000L);
    }
}
