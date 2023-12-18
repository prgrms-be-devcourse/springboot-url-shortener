package com.prgrms.url_shortener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.prgrms.url_shortener.algorithm.Base62Encoder;
import com.prgrms.url_shortener.dto.ShortenUrlRequest;
import com.prgrms.url_shortener.dto.ShortenUrlResponse;
import com.prgrms.url_shortener.entity.Url;
import com.prgrms.url_shortener.repository.UrlRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
class UrlServiceTest {
    @Mock
    private UrlRepository repository;

    @InjectMocks
    private UrlService service;

    @DisplayName("Base62인코딩으로 shortenUrl을 생성할 수 있다.")
    @Test
    void create_url() {
        //given
        String originUrl = "https://daum.net";
        ShortenUrlRequest request = new ShortenUrlRequest(originUrl);

        Url url = new Url(originUrl);
        ReflectionTestUtils.setField(url, "id", 1L);

        when(repository.existsByOriginUrl(originUrl)).thenReturn(false);
        when(repository.save(any(Url.class))).thenReturn(url);
        String shortUri = Base62Encoder.encode(1L);

        //when
        ShortenUrlResponse response = service.getShortUrl(request);

        //then
        assertThat(response.shortenUrl()).endsWith(shortUri);
    }


    @DisplayName("shortUri을 디코딩하여 DB에 저장된 originUrl을 가져올 수 있다.")
    @Test
    void get_origin_url() {
        //given
        String expectedOriginUrl = "https://daum.net";
        Url url = new Url(expectedOriginUrl);
        ReflectionTestUtils.setField(url, "id", 1L);

        String shortUri = Base62Encoder.encode(1L);

        when(repository.findById(any(Long.class))).thenReturn(Optional.of(url));

        //when
        String actualOriginUrl = service.getOriginUrl(shortUri);

        //then
        assertThat(actualOriginUrl).isEqualTo(expectedOriginUrl);
    }
}