package devcourse.springbooturlshortener.entity;

import devcourse.springbooturlshortener.entity.vo.OriginalUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlTest {

    @Test
    @DisplayName("URL 생성 시 OriginalUrl이 정상적으로 설정된다.")
    void createUrlWithOriginalUrl() {
        // Given
        OriginalUrl originalUrl = new OriginalUrl("http://www.example.com");

        // When
        Url url = Url.builder().originalUrl(originalUrl).build();

        // Then
        assertThat(url.getOriginalUrl()).isEqualTo(originalUrl);
    }

    @Test
    @DisplayName("Short URL이 업데이트되면 제대로 업데이트된다.")
    void updateShortUrl() {
        // Given
        OriginalUrl originalUrl = new OriginalUrl("http://www.example.com");
        Url url = Url.builder().originalUrl(originalUrl).build();
        String newShortUrl = "abc123";

        // When
        url.updateShortUrl(newShortUrl);

        // Then
        assertThat(url.getShortUrl()).isEqualTo(newShortUrl);
    }

    @Test
    @DisplayName("URL이 생성될 때 기본 Hit 값은 0이다.")
    void defaultHitValueWhenUrlIsCreated() {
        // Given
        OriginalUrl originalUrl = new OriginalUrl("http://www.example.com");

        // When
        Url url = Url.builder().originalUrl(originalUrl).build();

        // Then
        assertThat(url.getHit()).isZero();
    }
}