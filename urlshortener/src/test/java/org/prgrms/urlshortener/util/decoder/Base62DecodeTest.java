package org.prgrms.urlshortener.util.decoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;
import org.prgrms.urlshortener.respository.UrlRepository;

@ExtendWith(MockitoExtension.class)
class Base62DecodeTest {

    @Mock
    UrlRepository urlRepository;

    @InjectMocks
    Base62Decode base62Decode;

    @DisplayName("암호화된 Url을 복호화한다.")
    @MethodSource("encodedUrls")
    @ParameterizedTest
    void Should_Decode_When_EncodedUrlIsPassed(String encodedUrl, Url url) {
        // given
        given(urlRepository.findByAlgorithmAndEncodedUrl(Algorithm.BASE_62, encodedUrl))
            .willReturn(Optional.ofNullable(url));

        // when
        String decode = base62Decode.decode(encodedUrl);

        // then
        assertThat(decode).isEqualTo(url.getOriginUrl());
    }

    static Stream<Arguments> encodedUrls() {
        return Stream.of(
            Arguments.arguments("bent.ly/0", new Url("https://www.naver.com", Algorithm.BASE_62)),
            Arguments.arguments("bent.ly/1", new Url("https://www.google.com", Algorithm.BASE_62)),
            Arguments.arguments("bent.ly/2", new Url("https://www.acmicpc.net/", Algorithm.BASE_62))
        );
    }

}
