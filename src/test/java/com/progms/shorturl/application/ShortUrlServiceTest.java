package com.progms.shorturl.application;

import com.progms.shorturl.dto.UrlRequest;
import com.progms.shorturl.dto.UrlResponse;
import com.progms.shorturl.entity.ShortUrl;
import com.progms.shorturl.repository.ShortUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceTest {

    @InjectMocks
    ShortUrlService shortUrlService;

    @Spy
    UrlGenerationService urlGenerationService;

    @Mock
    ShortUrlRepository shortUrlRepository;

    UrlRequest urlRequest;

    @BeforeEach
    void init() {
        urlRequest = spy(new UrlRequest("https://test.com/testtesttesttesttesttesttesttest"));
    }

    @DisplayName("url 변환 성공")
    @Test
    void generateUrl_success() {
        // given
        Long uniqueId = 1L;
        String parseUrl = "1";

        ShortUrl shortUrl = spy(urlRequest.toEntity());

        given(urlRequest.toEntity()).willReturn(shortUrl);
        given(shortUrl.getShortId()).willReturn(uniqueId);

        // when
        UrlResponse urlResponse = shortUrlService.generateUrl(urlRequest);

        // then
        assertThat(parseUrl).isEqualTo(urlResponse.shortUrl());
        verify(shortUrlRepository, times(1)).save(any(ShortUrl.class));
    }

    @DisplayName("")
    @Test
    void existedUrl_and_get_success() {
        // given
        Long uniqueId = 1L;
        String parseUrl = "1";

        UrlRequest urlRequest = spy(new UrlRequest("https://test.com/testtesttesttesttesttesttesttest"));
        ShortUrl shortUrl = spy(urlRequest.toEntity());
        shortUrl.updateShortUrl(parseUrl);

        given(shortUrlRepository.findByOriginUrl(any(String.class))).willReturn(Optional.of(shortUrl));

        // when
        UrlResponse urlResponse = shortUrlService.generateUrl(urlRequest);

        // then
        assertThat(urlResponse.shortUrl()).isEqualTo(parseUrl);
        verify(shortUrl, times(1)).updateView();
    }
}