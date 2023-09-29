package com.prgrms.urlshortener.service;

import com.prgrms.urlshortener.domain.Urls;
import com.prgrms.urlshortener.utils.URLShorteningStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @Mock
    private UrlServiceHelper urlServiceHelper;

    @Mock
    private Map<String, URLShorteningStrategy> strategies;

    @InjectMocks
    private UrlServiceImpl urlService;

    @DisplayName("단축 url을 생성할 수 있다.")
    @Test
    void createShortenUrl_ShouldReturnValidShortenUrl() {
        // Given
        URLShorteningStrategy mockStrategy = mock(URLShorteningStrategy.class);
        when(strategies.get(anyString())).thenReturn(mockStrategy);
        when(urlServiceHelper.generateUniqueShortenUrl(any(), anyString(), anyInt())).thenReturn("shortenUrl");

        // When
        String result = urlService.createShortenUrl("https://www.example.com", "mockStrategy");

        // Then
        Assertions.assertThat(result).isEqualTo("shortenUrl");
        verify(urlServiceHelper, times(1)).saveShortenedUrl("https://www.example.com", "shortenUrl");
    }

    @DisplayName("단축 url을 originUrl을 입력받아 반환할 수 있다.")
    @Test
    void getOriginalUrl_ShouldReturnOriginalUrl() {
        // Given
        Urls mockUrls = new Urls("https://www.example.com", "shortenUrl");
        when(urlServiceHelper.findUrlByShortenUrl("shortenUrl")).thenReturn(mockUrls);
        when(urlServiceHelper.ensureUrlHasProtocol("https://www.example.com")).thenReturn("https://www.example.com");

        // When
        String result = urlService.getOriginalUrl("shortenUrl");

        // Then
        Assertions.assertThat(result).isEqualTo("https://www.example.com");
    }
}