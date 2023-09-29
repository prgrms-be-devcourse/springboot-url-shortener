package com.prgrms.urlshortener.service;

import com.prgrms.urlshortener.dao.UrlRepository;
import com.prgrms.urlshortener.domain.Urls;
import com.prgrms.urlshortener.utils.URLShorteningStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceHelperTest {

    @InjectMocks
    private UrlServiceHelper urlServiceHelper;

    @Mock
    private UrlRepository urlRepository;

    @Test
    void generateUniqueShortenUrl_ShouldReturnShortenUrl() {
        // Given
        URLShorteningStrategy mockStrategy = mock(URLShorteningStrategy.class);
        when(mockStrategy.shortenURL(any())).thenReturn("shortenUrl");
        when(urlRepository.findByShortenUrl(any())).thenReturn(Optional.empty());

        // When
        String result = urlServiceHelper.generateUniqueShortenUrl(mockStrategy, "https://www.example.com", 5);

        // Then
        Assertions.assertThat(result).isEqualTo("shortenUrl");
        verify(urlRepository, times(1)).findByShortenUrl("shortenUrl");
    }


    @Test
    void saveShortenedUrl() {
        //given
        String originUrl = "www.example.com";
        String shortenUrl = "asdfasd";

        //when
        urlServiceHelper.saveShortenedUrl(originUrl, shortenUrl);

        //then
        verify(urlRepository, times(1)).save(any());
    }

    @Test
    void findUrlByShortenUrl() {
        //given
        String originUrl = "www.example.com";
        String shortenUrl = "asdfasd";
        when(urlRepository.findByShortenUrl(shortenUrl)).thenReturn(Optional.of(new Urls(originUrl, shortenUrl)));
        //when
        urlServiceHelper.findUrlByShortenUrl(shortenUrl);

        //then
        verify(urlRepository, times(1)).findByShortenUrl(shortenUrl);
    }

    @Test
    void ensureUrlHasProtocol() {
        //given
        String originUrl = "www.example.com";

        //when
        String result = urlServiceHelper.ensureUrlHasProtocol(originUrl);

        //then
        Assertions.assertThat(result).contains("http");
    }
}