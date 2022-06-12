package shortUrl.shortUrl.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import shortUrl.shortUrl.domain.value.Algorithm;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UrlTest {

    @BeforeEach
    void setup() {
        String originalUrl = "naver.com";
        url = new Url(originalUrl, Algorithm.BASE_56);
    }

    Url url;

    @Test
    @DisplayName("성공 : 조회수 증가")
    public void 조회수_증가() {
        Long hits = url.getHits();
        assertThat(hits).isEqualTo(0L);
        url.increaseHits();
        Long afterHits = url.getHits();
        assertThat(afterHits).isEqualTo(1L);
    }

    @Test
    @DisplayName("성공 : url 저장")
    public void 축약_url_저장() {
        String shortUrl = "shortUrl";
        url.saveShortUrl(shortUrl);

        assertThat(url.getShortUrl()).isEqualTo(shortUrl);
    }

    @Test
    @DisplayName("예외 발생 : url 변경 감지")
    public void 축약_url_변경_감지() {
        String shortUrl = "shortUrl";
        url.saveShortUrl(shortUrl);

        String newShortUrl = "new shortUrl";

        Assertions.assertThrows(RuntimeException.class, () -> url.saveShortUrl(newShortUrl));
    }
}