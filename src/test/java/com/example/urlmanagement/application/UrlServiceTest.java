package com.example.urlmanagement.application;

import com.example.urlmanagement.domain.Url;
import com.example.urlmanagement.dto.request.CreateShortUrlRequest;
import com.example.urlmanagement.exception.UrlErrorCode;
import com.example.urlmanagement.exception.UrlException;
import com.example.urlmanagement.repository.UrlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @Test
    @DisplayName("특정 URL에 대한 short URL을 생성할 수 있다.")
    void testCreateShortUrl() throws Exception {
        // given
        String BASE_URL = "null";
        CreateShortUrlRequest createShortUrlRequest = new CreateShortUrlRequest("https://letskuku.com", "base58");
        Url url = new Url(createShortUrlRequest.getOriginalUrl());
        Long urlId = 1234567L;

        Field id = url.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(url, urlId);

        when(urlRepository.save(any(Url.class)))
                .thenReturn(url);
        when(urlRepository.findByOriginalUrl(createShortUrlRequest.getOriginalUrl()))
                .thenReturn(Optional.of(url));

        // when
        String shortUrl = urlService.createShortUrl(createShortUrlRequest);
        Url savedUrl = urlRepository.findByOriginalUrl(createShortUrlRequest.getOriginalUrl()).get();

        // then
        assertThat(shortUrl).isEqualTo(BASE_URL + savedUrl.getShortUrl());
    }

    @Test
    @DisplayName("특정 short URL에 알맞은 original URL을 찾을 수 있다.")
    void testGetOriginalUrl() throws Exception {
        // given
        Url url = new Url("https://letskuku.com");
        Long urlId = 1234567L;
        String shortUrl = "Ablka";

        Field id = url.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(url, urlId);

        Field shortUrlField = url.getClass().getDeclaredField("shortUrl");
        shortUrlField.setAccessible(true);
        shortUrlField.set(url, shortUrl);

        when(urlRepository.findByShortUrl(shortUrl))
                .thenReturn(Optional.of(url));

        // when
        String originalUrl = urlService.getOriginalUrl(shortUrl);

        // then
        assertThat(originalUrl).isEqualTo(url.getOriginalUrl());
    }

    @Test
    @DisplayName("존재하지 않는 URL에 대한 조회는 예외를 발생시킨다.")
    void testUrlNotFoundException() {
        // given
        String shortUrl = "alkgj";

        when(urlRepository.findByShortUrl(shortUrl))
                .thenThrow(new UrlException(UrlErrorCode.URL_NOT_FOUND, shortUrl));

        // when - then
        assertThatThrownBy(() -> urlService.getOriginalUrl(shortUrl))
                .isInstanceOf(UrlException.class)
                .hasFieldOrPropertyWithValue("errorCode", UrlErrorCode.URL_NOT_FOUND);
    }
}
