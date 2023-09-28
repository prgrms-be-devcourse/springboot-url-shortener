package com.prgrms.urlshortener.service;

import com.prgrms.urlshortener.dao.UrlRepository;
import com.prgrms.urlshortener.domain.Urls;
import com.prgrms.urlshortener.utils.HashStrategy;
import com.prgrms.urlshortener.utils.URLShorteningStrategy;
import org.assertj.core.api.Assertions;
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
    private UrlRepository urlRepository;

    @Mock
    private Map<String, URLShorteningStrategy> strategies;

    @InjectMocks
    private UrlServiceImpl urlService;

    private final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Test
    void createShortenUrl_ShouldReturnValidShortenUrl() {
        //given
        HashStrategy strategy = new HashStrategy(BASE62);
        when(urlRepository.findByShortenUrl(anyString())).thenReturn(Optional.empty());
        when(strategies.get(any(String.class))).thenReturn(strategy);

        //when
        String result = urlService.createShortenUrl("https://www.example.com", "mockStrategy");

        //then
        Assertions.assertThat(result.length()).isEqualTo(7);
        verify(urlRepository, times(1)).save(any(Urls.class));
    }
}