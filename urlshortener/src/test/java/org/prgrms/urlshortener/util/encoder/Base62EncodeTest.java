package org.prgrms.urlshortener.util.encoder;

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
import org.prgrms.urlshortener.domain.Url;
import org.prgrms.urlshortener.respository.UrlRepository;

@ExtendWith(MockitoExtension.class)
class Base62EncodeTest {

    @InjectMocks
    Base62Encode base62Encode;

    @Mock
    UrlRepository urlRepository;

    @Mock
    Url url;

    @DisplayName("기존 URL을 암호화시킬수 있다.")
    @MethodSource("originUrls")
    @ParameterizedTest
    void Should_encode_When_OriginUrlIsPassed(String originUrl, String encodedUrl, Long id) {
        // given
        given(url.getId()).willReturn(id);
        given(urlRepository.findByOriginUrl(originUrl)).willReturn(Optional.ofNullable(url));

        // when
        String encode = base62Encode.encode(originUrl);

        // then
        assertThat(encodedUrl).isEqualTo(encode);
    }

    static Stream<Arguments> originUrls() {
        return Stream.of(
            Arguments.arguments("https://www.naver.com", "0", 1L),
            Arguments.arguments("https://www.google.com", "1", 2L),
            Arguments.arguments("https://www.acmicpc.net/", "2", 3L)
        );
    }

}
