package com.prgrms.wonu606.shorturl.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateParam;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DefaultUrlShortenerServiceTest {

    @Autowired
    private DefaultUrlShortenerService defaultUrlShortenerService;

    @Autowired
    private UrlLinkRepository urlLinkRepository;

    @Nested
    class FindOrCreateShortenUrlHashMethodTests {

        @ParameterizedTest
        @MethodSource("provideValidUrls")
        void givenValidParam_thenCreatingUrlLink(ShortenUrlCreateParam param) {
            // When
            ShortenUrlCreateResult result = defaultUrlShortenerService.findOrCreateShortenUrlHash(param);
            UrlHash createdUrlHash = new UrlHash(result.hashedShortUrl());

            // Then
            assertThat(urlLinkRepository.existByUrlHash(createdUrlHash)).isTrue();
        }

        @ParameterizedTest
        @MethodSource("provideValidUrls")
        void whenUrlExists_thenReturnExistingHash(ShortenUrlCreateParam param) {
            // When
            defaultUrlShortenerService.findOrCreateShortenUrlHash(param);
            ShortenUrlCreateResult result = defaultUrlShortenerService.findOrCreateShortenUrlHash(param);

            // Then
            assertThat(result.isNew()).isFalse();
        }


        static Stream<Arguments> provideValidUrls() throws Exception {
            ValidUrlStringArgumentsProvider validUrlStringArgumentsProvider = new ValidUrlStringArgumentsProvider();
            List<String> validUrlStringList = validUrlStringArgumentsProvider.provideArguments(null)
                    .map(arguments -> arguments.get()[0].toString())
                    .toList();

            List<Arguments> argumentsList = new ArrayList<>();
            for (String validUrlString : validUrlStringList) {
                argumentsList.add(Arguments.of(new ShortenUrlCreateParam(validUrlString)));
            }
            return argumentsList.stream();
        }
    }

    @Nested
    class GetOriginalUrlByShortUrlMethodTests {

        @ParameterizedTest
        @ArgumentsSource(ValidUrlStringArgumentsProvider.class)
        void whenShortUrlExists_thenReturnOriginalUrl(String originalUrl) {
            // When
            ShortenUrlCreateResult shortenUrlCreateResult =
                    defaultUrlShortenerService.findOrCreateShortenUrlHash(new ShortenUrlCreateParam(originalUrl));

            String actualOriginalUrl =
                    defaultUrlShortenerService.getOriginalUrlByShortUrl(shortenUrlCreateResult.hashedShortUrl());

            // Then
            assertThat(actualOriginalUrl).isEqualTo(originalUrl);
        }

        @Test
        void whenShortUrlDoesNotExist_thenThrowException() {
            // Given
            String nonExistentShortUrl = "GSSORWLL";
            // When & Then
            assertThatThrownBy(() ->
                    defaultUrlShortenerService.getOriginalUrlByShortUrl(nonExistentShortUrl))
                    .isInstanceOf(UrlNotFoundException.class)
                    .hasMessage("존재 하지 않는 Short URL입니다. Current Short Url: " + nonExistentShortUrl);
        }
    }

    static class ValidUrlStringArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of("https://url.kr"),
                    Arguments.of("https://www.stackoverflow.com"),
                    Arguments.of("https://comic.naver.com/index"),
                    Arguments.of("https://www.youtube.com/watch?v=Yc56NpYW1DM"),
                    Arguments.of("https://www.youtube.com/@devbadak")
            );
        }
    }
}
