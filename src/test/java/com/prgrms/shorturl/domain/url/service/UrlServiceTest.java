package com.prgrms.shorturl.domain.url.service;

import com.prgrms.shorturl.algorithm.AlgorithmType;
import com.prgrms.shorturl.algorithm.Base62;
import com.prgrms.shorturl.domain.url.dto.request.UrlCreateRequestDTO;
import com.prgrms.shorturl.domain.url.dto.response.UrlResponseDTO;
import com.prgrms.shorturl.domain.url.entity.Url;
import com.prgrms.shorturl.domain.url.repository.UrlRepository;
import com.prgrms.shorturl.common.exception.ErrorCode;
import com.prgrms.shorturl.common.exception.BaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @InjectMocks
    UrlService urlService;

    @Mock
    UrlRepository urlRepository;

    @Mock
    Base62 base62Algorithm;

    private Url url;

    private final Long savedUrlId = 1L;

    @BeforeEach
    void setUp() {
        url = Url.builder()
                .originUrl("www.daangn.com")
                .algorithmType(AlgorithmType.BASE62)
                .build();
    }

    @Test
    @DisplayName("[성공] 첫 요청일 때, shortURL 을 만들어낸다.")
    void makeShortUrlTest() {
        //given
        UrlCreateRequestDTO urlCreateRequestDTO = UrlCreateRequestDTO.builder()
                .originUrl(url.getOriginUrl())
                .algorithmType(url.getAlgorithmType())
                .build();

        //mocking
        url = mock(Url.class);
        when(urlRepository.save(any(Url.class))).thenReturn(url);
        when(url.getId()).thenReturn(savedUrlId);


        //when
        UrlResponseDTO urlResponseDTO = urlService.createShortUrl(urlCreateRequestDTO);

        //then
        Assertions.assertEquals(url.getOriginUrl(), urlResponseDTO.originUrl());
        Assertions.assertEquals(url.getShortUrl(), urlResponseDTO.shortUrl());
    }

    @Test
    @DisplayName("[성공] 두번째 이상 요청일 일때 origin url 조회에 성공한다.")
    void getOriginUrlTest() {
        //given
        UrlCreateRequestDTO urlCreateRequestDTO = UrlCreateRequestDTO.builder()
                .originUrl(url.getOriginUrl())
                .algorithmType(url.getAlgorithmType())
                .build();

        url = mock(Url.class);
        when(urlRepository.save(any(Url.class))).thenReturn(url);
        when(url.getId()).thenReturn(savedUrlId);

        UrlResponseDTO urlResponseDTO = urlService.createShortUrl(urlCreateRequestDTO);

        Long requestCount = 1L;

        //mocking
        url = mock(Url.class);
        when(urlRepository.findByShortUrl(urlResponseDTO.shortUrl())).thenReturn(Optional.of(url));
        when(url.getOriginUrl()).thenReturn(urlResponseDTO.originUrl());
        when(url.getShortUrl()).thenReturn(urlResponseDTO.shortUrl());
        when(url.getRequestCount()).thenReturn(requestCount);

        UrlResponseDTO urlResponseDTO1 = urlService.getOriginUrl(urlResponseDTO.shortUrl());

        //then
        assertThat(urlResponseDTO1)
                .hasFieldOrPropertyWithValue("originUrl", urlResponseDTO.originUrl())
                .hasFieldOrPropertyWithValue("shortUrl", urlResponseDTO.shortUrl())
                .hasFieldOrPropertyWithValue("requestCount", requestCount);
    }

    @Test
    @DisplayName("[예외] shortUrl에 해당하는 URL이 없으면 예외를 발생시킨다.")
    void notFoundUrlTest() {
        //given
        String unknownUrl = "AAAAAABC";

        when(urlRepository.findByShortUrl(unknownUrl)).thenReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> {
            urlService.getOriginUrl(unknownUrl);
        }).isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.NOT_FOUND_URL);
    }
}