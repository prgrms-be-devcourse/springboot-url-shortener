package com.progms.shorturl.application;

import com.progms.shorturl.dto.UrlRequest;
import com.progms.shorturl.dto.UrlResponse;
import com.progms.shorturl.entity.ShortUrl;
import com.progms.shorturl.repository.ShortUrlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @DisplayName("url 변환 성공")
    @Test
    void generateUrl_success() {
        // given
        Long uniqueId = 1L;
        String parseUrl = "1";

        UrlRequest urlRequest = spy(new UrlRequest("https://test.com/testtesttesttesttesttesttesttest"));
        ShortUrl shortUrl = spy(urlRequest.toEntity());

        given(urlRequest.toEntity()).willReturn(shortUrl);
        given(shortUrl.getShortId()).willReturn(uniqueId);

        // when
        UrlResponse urlResponse = shortUrlService.generateUrl(urlRequest);

        // then
        assertThat(parseUrl).isEqualTo(urlResponse.shortUrl());
        verify(shortUrlRepository).save(any(ShortUrl.class));
    }
}