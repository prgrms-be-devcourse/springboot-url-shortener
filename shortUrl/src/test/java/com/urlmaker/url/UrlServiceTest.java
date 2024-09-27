package com.urlmaker.url;

import com.urlmaker.dto.UrlCreateRequestDTO;
import com.urlmaker.dto.UrlCreateResponseDTO;
import com.urlmaker.dto.UrlGetResponseDTO;
import com.urlmaker.algorithm.Algorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private Algorithm algorithm;

    @Nested
    class urlCreateAndCreateAgainTest{
        UrlCreateRequestDTO req = new UrlCreateRequestDTO(
                "www.naver.com",
                "Base62"
        );
        Url savedUrl = new Url(
                "www.naver.com",
                "Base62"
        );

        @Test
        @DisplayName("shorturl 생성에 성공한다.")
        void createShortenUrl() {
            // given
            ReflectionTestUtils.setField(
                    savedUrl,
                    "urlId",
                    1L
            );

            given(urlRepository.save(any(Url.class))).willReturn(savedUrl);

            //when
            UrlCreateResponseDTO res = urlService.createShortenUrl(req);

            //then
            assertThat(res.shortenUrl()).isEqualTo(algorithm.encode(savedUrl.getUrlId()));
            assertThat(res.requestCount()).isEqualTo(savedUrl.getRequestCount());

        }

        @Test
        @DisplayName("shortUrl을 같은 주소로 다시 시도한 경우 requestCouont 증가에 성공한다.")
        void createShortenUrlAgain() {
            //given
            ReflectionTestUtils.setField(
                    savedUrl,
                    "urlId",
                    1L
            );

            given(urlRepository.findByOriginUrl(anyString())).willReturn(Optional.ofNullable(savedUrl));

            //when
            UrlCreateResponseDTO res = urlService.createShortenUrl(req);

            //then
            assertThat(res.shortenUrl()).isEqualTo(algorithm.encode(savedUrl.getUrlId()));
            assertThat(res.requestCount()).isEqualTo(savedUrl.getRequestCount());
        }
    }

    @Test
    @DisplayName("shorturl로 조회시 원래 주소로 리다이렉트에 성공한다.")
    void getOriginUrl() {
        //given
        String shortenUrl = "OjkvGAAA";
        Url savedUrl = new Url(
                "www.naver.com",
                "Base62"
        );
        ReflectionTestUtils.setField(
                savedUrl,
                "urlId",
                1L
        );

        given(urlRepository.findById(anyLong())).willReturn(Optional.of(savedUrl));

        //when
        UrlGetResponseDTO res = urlService.getOriginUrl(shortenUrl);

        //then
        assertThat(res.originUrl()).isEqualTo(savedUrl.getOriginUrl());
        assertThat(res.requestCount()).isEqualTo(0);
    }
}