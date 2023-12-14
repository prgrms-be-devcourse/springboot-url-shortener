package com.example.shorturl.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.shorturl.domain.Algorithm;
import com.example.shorturl.domain.Url;
import com.example.shorturl.dto.request.ShortUrlCreateRequest;
import com.example.shorturl.repository.UrlRepository;
import com.example.shorturl.util.encoder.Encoder;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private Encoder encoder;

    @Mock
    private Url testUrl;

    @Test
    void shortUrl_기존_저장값_조회_테스트() {
        // Given
        ShortUrlCreateRequest request = new ShortUrlCreateRequest("https://www.example.com", Algorithm.BASE_62);
        given(urlRepository.findUrlByOriginUrlAndAlgorithm(request.originUrl(), request.algorithm())).willReturn(Optional.of(testUrl));

        // When
        urlService.createOrGetShortUrl(request);

        // Then
        verify(urlRepository, times(1)).findUrlByOriginUrlAndAlgorithm(request.originUrl(), request.algorithm());
    }

    @Test
    void shortUrl_생성_테스트() {
        // Given
        ShortUrlCreateRequest request = new ShortUrlCreateRequest("https://www.example.com", Algorithm.BASE_62);
        given(urlRepository.findUrlByOriginUrlAndAlgorithm(any(), any())).willReturn(Optional.empty());
        given(testUrl.getId()).willReturn(1L);
        given(urlRepository.save(any())).willReturn(testUrl);

        // When
        urlService.createOrGetShortUrl(request);

        // Then
        verify(urlRepository, times(1)).findUrlByOriginUrlAndAlgorithm(request.originUrl(), request.algorithm());
        verify(urlRepository, times(1)).save(any());
        verify(encoder, times(1)).encodeUrl(any(), any());
    }

    @Test
    void originUrl_조회_테스트() {
        // Given
        String shortUrl = "http://localhost:8080/test";
        given(urlRepository.findUrlByShortUrl(shortUrl)).willReturn(Optional.of(testUrl));

        // When
        urlService.getOriginUrl(shortUrl);

        // Then
        verify(urlRepository, times(1)).findUrlByShortUrl(shortUrl);
        verify(testUrl, times(1)).increaseRequestCount();
    }
}
