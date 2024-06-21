package org.prgrms.urlshortener.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.urlshortener.domain.Url;
import org.prgrms.urlshortener.dto.request.EncodedUrlCreateRequest;
import org.prgrms.urlshortener.dto.request.OriginUrlRequest;
import org.prgrms.urlshortener.dto.response.EncodedUrlCreateResponse;
import org.prgrms.urlshortener.dto.response.OriginUrlResponse;
import org.prgrms.urlshortener.fixture.dto.EncodedUrlCreateRequestBuilder;
import org.prgrms.urlshortener.fixture.dto.OriginUrlRequestBuilder;
import org.prgrms.urlshortener.fixture.entity.UrlBuilder;
import org.prgrms.urlshortener.respository.UrlRepository;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceTest {

    @InjectMocks
    ShortUrlService shortUrlService;

    @Mock
    UrlRepository urlRepository;

    @Mock
    Encoder encoder;

    @Mock
    Decoder decoder;

    @Nested
    class EncodeTest {

        @DisplayName("기존 url을 전달받으면 암호화시킬 수 있다.")
        @MethodSource("createRequestAndEncodedUrls")
        @ParameterizedTest
        void Should_EncodeUrl_When_OriginUrlIsPassed(EncodedUrlCreateRequest request, String encodedUrl, String expected) {
            // given
            given(urlRepository.existsByAlgorithmAndOriginUrl(request.algorithm(), request.originUrl()))
                .willReturn(false);
            given(urlRepository.save(any())).willReturn(request.toEntity());
            given(encoder.encode(request)).willReturn(encodedUrl);

            // when
            EncodedUrlCreateResponse response = shortUrlService.encodeUrl(request);

            // then
            assertThat(response.encodedUrl()).isEqualTo(expected);
            assertThat(response.originUrl()).isEqualTo(request.originUrl());
        }

        static Stream<Arguments> createRequestAndEncodedUrls() {
            return Stream.of(
                Arguments.arguments(EncodedUrlCreateRequestBuilder.createOne("https://www.naver.com"),
                    "0", "bent.ly/0"),
                Arguments.arguments(EncodedUrlCreateRequestBuilder.createOne("https://www.google.com"),
                    "1", "bent.ly/1"),
                Arguments.arguments(EncodedUrlCreateRequestBuilder.createOne("https://www.acmicpc.net/"),
                    "2", "bent.ly/2")
            );
        }

    }

    @Nested
    class DecodeTest {

        @DisplayName("암호화된 Url을 전달받으면 복호화시킬 수 있다.")
        @MethodSource("createRequestAndShortenedUrls")
        @ParameterizedTest
        void Should_DecodeUrl_When_EncodedUrlIsPassed(OriginUrlRequest request, String originUrl, Url url) {
            // given
            given(decoder.decode(request.shortUrl(), request.algorithm())).willReturn(originUrl);
            given(urlRepository.findByOriginUrl(originUrl)).willReturn(Optional.ofNullable(url));

            // when
            OriginUrlResponse response = shortUrlService.getOriginUrl(request);

            // then
            assertThat(response.originUrl()).isEqualTo(originUrl);
        }

        static Stream<Arguments> createRequestAndShortenedUrls() {
            return Stream.of(
                Arguments.arguments(OriginUrlRequestBuilder.createOne("https://www.bent.ly/0"),
                    "https://www.naver.com",
                    UrlBuilder.createOne("https://www.naver.com")),
                Arguments.arguments(OriginUrlRequestBuilder.createOne("https://www.bent.ly/1"),
                    "https://www.google.com",
                    UrlBuilder.createOne("https://www.google.com")),
                Arguments.arguments(OriginUrlRequestBuilder.createOne("https://www.bent.ly/2"),
                    "https://www.acmicpc.net/",
                    UrlBuilder.createOne("https://www.acmicpc.net/"))
            );
        }
    }


}
