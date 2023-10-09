package com.prgrms.wonu606.shorturl.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateParam;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateResult;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
    class findOrCreateShortenUrlHashMethodTests {

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


        static Stream<Arguments> provideValidUrls() {
            return Stream.of(
                    Arguments.of(new ShortenUrlCreateParam("https://url.kr")),
                    Arguments.of(new ShortenUrlCreateParam("https://www.stackoverflow.com")),
                    Arguments.of(new ShortenUrlCreateParam("https://comic.naver.com/index")),
                    Arguments.of(new ShortenUrlCreateParam("https://www.youtube.com/watch?v=Yc56NpYW1DM")),
                    Arguments.of(new ShortenUrlCreateParam("https://www.youtube.com/@devbadak"))
            );
        }
    }
}
