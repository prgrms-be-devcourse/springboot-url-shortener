package com.young.shortenerurl.url.application;

import com.young.shortenerurl.global.generator.UniqueKeyGenerator;
import com.young.shortenerurl.url.model.EncodingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EncodingExecutorTest {

    @InjectMocks
    private EncodingExecutor encodingExecutor;

    @Mock
    private UniqueKeyGenerator uniqueKeyGenerator;

    @Test
    @DisplayName("파라미터로 받은 base64V1 인코딩 알고리즘에 맞는 인코딩 값을 반환 한다.")
    void encode_base64V1() {
        //given
        given(uniqueKeyGenerator.generateKey()).willReturn(63L);

        //when
        String encodedUrl = encodingExecutor.encode(EncodingType.BASE_64_V1);

        //then
        assertThat(encodedUrl).isEqualTo("-");
    }

    @Test
    @DisplayName("파라미터로 받은 base64V2 인코딩 알고리즘에 맞는 인코딩 값을 반환 한다.")
    void encode_base64V2() {
        //given
        given(uniqueKeyGenerator.generateKey()).willReturn(63L);

        //when
        String encodedUrl = encodingExecutor.encode(EncodingType.BASE_62_V2);

        //then
        assertThat(encodedUrl).isEqualTo("~");
    }

}