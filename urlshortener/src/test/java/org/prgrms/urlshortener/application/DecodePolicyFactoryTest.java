package org.prgrms.urlshortener.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.respository.UrlRepository;
import org.prgrms.urlshortener.util.decoder.Base62Decode;
import org.prgrms.urlshortener.util.decoder.DecodePolicy;

@ExtendWith(MockitoExtension.class)
class DecodePolicyFactoryTest {

    @InjectMocks
    DecodePolicyFactory decodePolicyFactory;

    @Mock
    UrlRepository urlRepository;

    @DisplayName("알고리즘을 전달받으면 그에 맞는 복호화 정책을 반환한다.")
    @Test
    void Should_ReturnDecodePolicy_When_GivenAlgorithm() {
        // when
        DecodePolicy decodePolicy = decodePolicyFactory.createDecodePolicy(Algorithm.BASE_62);

        // then
        assertThat(decodePolicy).isInstanceOf(Base62Decode.class);
    }

}
