package com.example.urlshortenert2.service;

import com.example.urlshortenert2.ShorteningKeyMaker.ShorteningKeyMaker;
import com.example.urlshortenert2.dto.ShortenerRequestDto;
import com.example.urlshortenert2.dto.ShortenerResponseDto;
import com.example.urlshortenert2.model.ShortedUrl;
import com.example.urlshortenert2.repository.ShortedUrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ShortenerServiceTest {

    @Mock
    private ShortedUrlRepository repository;
    @Mock
    private ShorteningKeyMaker maker;

    @InjectMocks
    private ShortenerService shortenerService;

    @Test
    @DisplayName("짧은 url을 생성할 수 있다.")
    void short_url_생성테스트() {
        // given
        ShortenerRequestDto request = new ShortenerRequestDto("https://www.naver.com");
        ShortedUrl shortedUrl = new ShortedUrl(request.url());
        // when
        when(repository.save(any(ShortedUrl.class))).thenReturn(shortedUrl);
        when(maker.makeShorteningKey(shortedUrl.getId())).thenReturn("1");
        ShortenerResponseDto response = shortenerService.createShortener(request);
        // then
        verify(repository).save(any(ShortedUrl.class));
        verify(maker).makeShorteningKey(shortedUrl.getId());
        Assertions.assertThat(response.shorteningKey()).isEqualTo("1");
    }

    @Test
    @DisplayName("shortening key를 이용해서 특정 기존 진짜 URL을 찾을 수 있다.")
    void shortening_key_조회테스트() {
        // given
        ShortedUrl responseUrl = new ShortedUrl("https://www.naver.com");
        when(repository.findShortedUrlByShorteningKey(anyString())).thenReturn(Optional.of(responseUrl));
        // when
        String originUrl = shortenerService.findByShorteningKey(anyString());
        // then
        verify(repository).findShortedUrlByShorteningKey(anyString());
        Assertions.assertThat(originUrl).isEqualTo(responseUrl.getOriginUrl());
    }
}