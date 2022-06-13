package com.prgrms.urlshortener.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.urlshortener.domain.ShortedUrl;
import com.prgrms.urlshortener.domain.Url;
import com.prgrms.urlshortener.dto.CreateShortenUrlRequest;
import com.prgrms.urlshortener.dto.UrlResponse;
import com.prgrms.urlshortener.repository.UrlRepository;
import com.prgrms.urlshortener.service.encoder.UrlEncoder;
import com.prgrms.urlshortener.service.encoder.UrlEncoders;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlEncoders urlEncoders;

    @Mock
    private UrlEncoder urlEncoder;

    @DisplayName("단축된 Url을 생성한다.")
    @Test
    void createShortedUrl() {
        // given
        CreateShortenUrlRequest request = new CreateShortenUrlRequest("https://studyhardd.tistory.com/7", "BASE62");
        given(urlRepository.save(any(Url.class))).willReturn(createUrl());
        given(urlEncoders.getUrlEncoderByType(anyString())).willReturn(urlEncoder);
        given(urlEncoder.encode(anyLong())).willReturn("2TX");

        // when
        String shortedUrl = urlService.createShortedUrl(request);

        // then
        assertThat(shortedUrl).isEqualTo("2TX");

        then(urlRepository).should(times(1)).save(any(Url.class));
        then(urlEncoders).should(times(1)).getUrlEncoderByType(anyString());
        then(urlEncoder).should(times(1)).encode(anyLong());
    }

    @DisplayName("단축된 Url로 원본 Url를 찾고 카운팅한다.")
    @Test
    void getOriginUrl() {
        // given
        String shortedUrl = "12345";
        Url url = createUrl_WithShortedUrl(shortedUrl);
        given(urlRepository.findUrlByShortedUrl(any(ShortedUrl.class))).willReturn(Optional.of(url));

        // when
        String originUrl = urlService.getOriginUrl(shortedUrl);

        // then
        assertThat(url.getOriginUrl()).isEqualTo(originUrl);
        assertThat(url.getRequestCount()).isEqualTo(1);

        then(urlRepository).should(times(1)).findUrlByShortedUrl(any(ShortedUrl.class));
    }

    @DisplayName("존재하지 않는 단축된 Url로 원본 Url를 찾을 수 없다.")
    @Test
    void getOriginUrl_InvalidShortedUrl() {
        // given
        String shortedUrl = "12345";
        given(urlRepository.findUrlByShortedUrl(any(ShortedUrl.class))).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> urlService.getOriginUrl(shortedUrl))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("단축 Url로 Url 정보를 조회한다.")
    @Test
    void getUrlInfromation() {
        // given
        String shortedUrl = "12345";
        Url url = createUrl_WithShortedUrl(shortedUrl);
        given(urlRepository.findUrlByShortedUrl(any(ShortedUrl.class))).willReturn(Optional.of(url));

        // when
        UrlResponse urlResponse = urlService.getUrlInformation(shortedUrl);

        // then
        assertThat(urlResponse.getRequestCount()).isEqualTo(url.getRequestCount());

        then(urlRepository).should(times(1)).findUrlByShortedUrl(any(ShortedUrl.class));
    }

    private Url createUrl() {
        return new Url(11157L, "https://studyhardd.tistory.com/7");
    }

    private Url createUrl_WithShortedUrl(String shortedUrl) {
        return new Url(11157L, "https://studyhardd.tistory.com/7", new ShortedUrl(shortedUrl));
    }

}
