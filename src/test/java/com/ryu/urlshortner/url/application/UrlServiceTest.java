package com.ryu.urlshortner.url.application;

import com.ryu.urlshortner.common.encoder.Base62Encoder;
import com.ryu.urlshortner.common.exception.UrlExpiredException;
import com.ryu.urlshortner.common.exception.UrlNotFoundException;
import com.ryu.urlshortner.url.application.UrlService;
import com.ryu.urlshortner.url.application.dto.request.UrlTransformDto;
import com.ryu.urlshortner.url.application.dto.response.UrlDto;
import com.ryu.urlshortner.url.domain.Url;
import com.ryu.urlshortner.url.domain.repository.UrlRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @InjectMocks
    UrlService urlService;

    @Mock
    UrlRepository urlRepository;

    @Mock
    Base62Encoder encoder;

    @Nested
    class transform_메소드는 {

        @Nested
        class 전달인자가_null이면 {

            @Test
            void IllegalArgumentException이_발생한다() {
                //given
                final UrlTransformDto urlTransformDto = null;

                //when, then
                assertThrows(IllegalArgumentException.class, () -> urlService.transform(urlTransformDto));
            }
        }

        @Nested
        class originUrl가_DB에서_조회가_되었다면 {

            @Test
            void 조회된_축약_Url을_반환한다() {
                //given
                final String originUrl = "https://www.test.com";
                final String shortUrl = "http://localhost:8080/api/v1/urls/TEST123";
                final LocalDateTime expiredAt = LocalDateTime.of(
                        2022, 12, 24, 00, 00, 00
                );
                final UrlTransformDto urlTransformDto = UrlTransformDto.builder()
                        .originUrl(originUrl)
                        .expiredAt(expiredAt)
                        .build();
                final Url url = Url.builder()
                        .seq(10000000L)
                        .originUrl(originUrl)
                        .shortUrl(shortUrl)
                        .expiredAt(expiredAt)
                        .build();
                final UrlDto urlDto = UrlDto.builder()
                        .originUrl(originUrl)
                        .shortUrl(shortUrl)
                        .build();
                doReturn(Optional.of(url)).when(urlRepository).findByOriginUrl(originUrl);

                //when
                final UrlDto foundUrlDto = urlService.transform(urlTransformDto);

                //then
                verify(urlRepository, times(1)).findByOriginUrl(originUrl);
                verify(encoder, times(0)).encode(anyLong());
                verify(urlRepository, times(0)).save(any(Url.class));
                assertThat(foundUrlDto).usingRecursiveComparison().isEqualTo(urlDto);
            }
        }

        @Nested
        class originUrl가_DB에서_조회가_안되었다면 {

            @Test
            void repository_save_메소드를_호출하고_축약_Url을_반환한다() {
                //given
                final String originUrl = "https://www.test.com";
                final String shortUrl = "http://localhost:8080/api/v1/urls/TEST123";
                final LocalDateTime expiredAt = LocalDateTime.of(
                        2022, 12, 24, 00, 00, 00
                );
                final UrlTransformDto urlTransformDto = UrlTransformDto.builder()
                        .originUrl(originUrl)
                        .expiredAt(expiredAt)
                        .build();
                final Url url = Url.builder()
                        .seq(10000000L)
                        .originUrl(originUrl)
                        .shortUrl(shortUrl)
                        .expiredAt(expiredAt)
                        .build();
                final UrlDto urlDto = UrlDto.builder()
                        .originUrl(originUrl)
                        .shortUrl(shortUrl)
                        .build();
                doReturn(Optional.empty()).when(urlRepository).findByOriginUrl(originUrl);
                doReturn(url).when(urlRepository).save(any(Url.class));
                doReturn(shortUrl).when(encoder).encode(10000000L);

                //when
                final UrlDto generatedUrlDto = urlService.transform(urlTransformDto);

                //then
                verify(urlRepository, times(1)).findByOriginUrl(originUrl);
                verify(encoder, times(1)).encode(anyLong());
                verify(urlRepository, times(1)).save(any(Url.class));
                assertThat(generatedUrlDto).usingRecursiveComparison().isEqualTo(urlDto);
            }
        }
    }

    @Nested
    class getOriginUrl_메소드는 {

        @Nested
        class 전달인자가_null이면 {

            @Test
            void IllegalArgumentException이_발생한다() {
                //given
                final String shortUrl = null;

                //when, then
                assertThrows(IllegalArgumentException.class, () -> urlService.getOriginUrl(shortUrl));
            }
        }

        @Nested
        class shortUrl가_DB에서_조회가_안되었다면 {

            @Test
            void UrlNotFoundException이_발생한다 () {
                //given
                final String shortUrl = "http://localhost:8080/api/v1/urls/TEST123";
                final long seq = 10000000L;
                doReturn(seq).when(encoder).decode(shortUrl);
                doReturn(Optional.empty()).when(urlRepository).findBySeq(seq);

                //when, then
                assertThrows(UrlNotFoundException.class, () -> urlService.getOriginUrl(shortUrl));
            }
        }

        @Nested
        class 축약된_Url의_유효기간_만료되었다면 {

            @Test
            void UrlExpiredException이_발생한다 () {
                //given
                final String shortUrl = "http://localhost:8080/api/v1/urls/TEST123";
                final long seq = 10000000L;
                final Url url = Url.builder()
                        .seq(seq)
                        .originUrl("https://www.test.com")
                        .shortUrl(shortUrl)
                        .expiredAt(LocalDateTime.MIN)
                        .build();
                doReturn(seq).when(encoder).decode(shortUrl);
                doReturn(Optional.of(url)).when(urlRepository).findBySeq(seq);
                doNothing().when(urlRepository).delete(url);

                //when, then
                assertThrows(UrlExpiredException.class, () -> urlService.getOriginUrl(shortUrl));
                verify(urlRepository, times(1)).delete(url);
            }
        }

        @Nested
        class shortUrl가_DB에서_조회되고_유효기간이_만료되지_않았다면 {

            @Test
            void 원래의_Url을_반환한다() {
                //given
                final String shortUrl = "http://localhost:8080/api/v1/urls/TEST123";
                final String originUrl = "https://www.test.com";
                final long seq = 10000000L;
                final Url url = Url.builder()
                        .seq(seq)
                        .originUrl(originUrl)
                        .shortUrl(shortUrl)
                        .expiredAt(LocalDateTime.MAX)
                        .build();
                doReturn(seq).when(encoder).decode(shortUrl);
                doReturn(Optional.of(url)).when(urlRepository).findBySeq(seq);

                //when
                String foundOriginUrl = urlService.getOriginUrl(shortUrl);

                // then
                assertThat(foundOriginUrl).isEqualTo(originUrl);
                verify(urlRepository, times(0)).delete(url);
            }
        }
    }
}
