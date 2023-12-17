package devcourse.springbooturlshortener.service;

import devcourse.springbooturlshortener.dto.ShortUrlCreateRequest;
import devcourse.springbooturlshortener.dto.ShortUrlFindResponse;
import devcourse.springbooturlshortener.entity.Url;
import devcourse.springbooturlshortener.entity.vo.OriginalUrl;
import devcourse.springbooturlshortener.repository.UrlRepository;
import devcourse.springbooturlshortener.urlalgorithm.UrlAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UrlAlgorithm base62EncodeUrlAlgorithm;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Short URL을 이용하여 Original URL을 가져오고 Hit를 증가시킨다.")
    void getOriginalUrlAndIncreaseHit() {
        // Given
        String shortUrl = "abc123";
        Url url = createUrlWithOriginalUrl("http://www.example.com");
        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(url));

        // When
        String originalUrl = urlService.getOriginalUrlAndIncreaseHit(shortUrl);

        // Then
        assertThat(originalUrl).isEqualTo(url.getOriginalUrl().getValue());
        verify(urlRepository, times(1)).increaseHit(url.getId());
    }

    @Test
    @DisplayName("Short URL이 유효하지 않으면 예외가 발생한다.")
    void getOriginalUrlAndIncreaseHitInvalidShortUrl() {
        // Given
        String invalidShortUrl = "invalidShortUrl";
        when(urlRepository.findByShortUrl(invalidShortUrl)).thenReturn(Optional.empty());

        // When, Then
        assertThatThrownBy(() -> urlService.getOriginalUrlAndIncreaseHit(invalidShortUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid short url");
    }

    @Test
    @DisplayName("Original URL을 이용하여 Short URL을 생성한다.")
    void createShortUrl() {
        // Given
        String originalUrl = "http://www.example.com";
        ShortUrlCreateRequest request = new ShortUrlCreateRequest(originalUrl);
        when(urlRepository.findByOriginalUrl(any())).thenReturn(Optional.empty());
        when(urlRepository.save(any())).thenReturn(createUrlWithOriginalUrl(originalUrl));
        when(base62EncodeUrlAlgorithm.urlEncoder(any())).thenReturn("abc123");

        // When
        ShortUrlFindResponse response = urlService.createShortUrl(request);

        // Then
        assertThat(response).isNotNull();
        verify(urlRepository, times(1)).save(any());
        verify(base62EncodeUrlAlgorithm, times(1)).urlEncoder(any());
    }

    @Test
    @DisplayName("이미 존재하는 Original URL이면 생성하지 않고 존재하는 Short URL을 반환한다.")
    void createShortUrlExistingOriginalUrl() {
        // Given
        String originalUrl = "http://www.example.com";
        ShortUrlCreateRequest request = new ShortUrlCreateRequest(originalUrl);
        Url existingUrl = createUrlWithOriginalUrl(originalUrl);
        when(urlRepository.findByOriginalUrl(any())).thenReturn(Optional.of(existingUrl));

        // When
        ShortUrlFindResponse response = urlService.createShortUrl(request);

        // Then
        assertThat(response).isNotNull();
        verify(urlRepository, never()).save(any());
    }

    private Url createUrlWithOriginalUrl(String originalUrl) {
        return Url.builder()
                .originalUrl(new OriginalUrl(originalUrl))
                .build();
    }
}