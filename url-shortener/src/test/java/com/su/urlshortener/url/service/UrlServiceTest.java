package com.su.urlshortener.url.service;

import com.su.urlshortener.url.exception.UrlNotFoundException;
import com.su.urlshortener.url.dto.RequestUrlDto;
import com.su.urlshortener.url.entity.Url;
import com.su.urlshortener.url.repository.UrlRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.su.urlshortener.url.service.shortener.ShorteningAlgorithm.SEQUENCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {
    @InjectMocks
    UrlService urlService;
    @Mock
    UrlRepository urlRepository;

    String givenUrl = "https://www.google.com/";
    String givenToken = "00000011";

    @Test
    void makeShortUrl() {
        // given
        var url = Url.of(givenUrl, givenToken, SEQUENCE);
        var dto = RequestUrlDto.of(url.getAlgorithm(), url.getOriginUrl());
        given(urlRepository.save(any(Url.class))).willReturn(url);
        // when
        urlService.makeShortUrl(dto);
        // then
        then(urlRepository).should(times(1)).save(url);
    }

    @Nested
    class findOriginUrlByToken {
        @Test
        void 찾는_URL_값이_없으면_예외를_발생시킨다() {
            // given
            String invalidToken = "99999999";
            given(urlRepository.findByShotToken(any(String.class))).willReturn(Optional.empty());

            // when
            assertThatThrownBy(() -> urlService.findOriginUrlByToken(invalidToken))
                    // then
                    .isInstanceOf(UrlNotFoundException.class);
        }

        @Test
        void 토큰을_이용해_원본_URL을_찾을_수_있다() {
            // given
            var url = Url.of(givenUrl, givenToken, SEQUENCE);
            given(urlRepository.findByShotToken(any(String.class))).willReturn(Optional.of(url));

            // when
            var originUrl = urlService.findOriginUrlByToken(givenToken);

            // then
            then(urlRepository).should(times(1)).findByShotToken(givenToken);
            assertThat(originUrl).isEqualTo(givenUrl);
            assertThat(url.getVisitNum()).isEqualTo(1);
        }
    }

    @Nested
    class findUrlDtoByToken {
        @Test
        void 찾는_URL_값이_없으면_예외를_발생시킨다() {
            // given
            given(urlRepository.findByShotToken(any(String.class))).willReturn(Optional.empty());
            // when
            assertThatThrownBy(() -> urlService.findUrlDtoByToken("12345678"))
                    // then
                    .isInstanceOf(UrlNotFoundException.class);
        }

        @Test
        void 찾은_OriginURL에_대한_ResponseDTO를_반환한다() {
            // given
            var url = Url.of(givenUrl, givenToken, SEQUENCE);
            given(urlRepository.findByShotToken(any(String.class))).willReturn(Optional.of(url));

            // when
            var searchedOriginUrl = urlService.findUrlDtoByToken(givenToken);

            // then
            then(urlRepository).should(times(1)).findByShotToken(givenToken);
            assertThat(searchedOriginUrl.getOriginUrl()).isEqualTo(givenUrl);
        }
    }
}