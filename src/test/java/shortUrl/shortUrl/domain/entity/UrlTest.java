package shortUrl.shortUrl.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    public void 조회수_증가() {
        Long hits = url.getHits();
        assertThat(hits).isEqualTo(0L);
        url.hitCount();
        Long afterHits = url.getHits();
        assertThat(afterHits).isEqualTo(1L);
    }

    @Test
    public void 축약_url_저장() {
        String shortUrl = "shortUrl";
        url.saveShortUrl(shortUrl);

        assertThat(url.getShortUrl()).isEqualTo(shortUrl);
    }
}