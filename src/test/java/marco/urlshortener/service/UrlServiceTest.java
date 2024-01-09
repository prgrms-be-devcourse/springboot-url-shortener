package marco.urlshortener.service;

import marco.urlshortener.domain.Url;
import marco.urlshortener.dto.UrlRequest;
import marco.urlshortener.dto.UrlResponse;
import marco.urlshortener.repositoy.UrlRepository;
import marco.urlshortener.util.Base62;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UrlServiceTest {
    @Autowired
    UrlService urlService;
    @Autowired
    UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        urlRepository.deleteAll();
    }

    @DisplayName("이미 존재하는 단축된 url을 조회한다.")
    @Test
    void testGetExistingShortUrl() {
        // given
        String longUrl = "https://www.naver.com";
        UrlRequest urlRequest = new UrlRequest(longUrl);
        Url expectedUrl = urlRepository.save(new Url(longUrl));
        String shortUrlKey = Base62.encode(expectedUrl.getId());

        // when
        UrlResponse actualUrl = urlService.getShortUrl(urlRequest);

        // then
        assertThat(actualUrl).isNotNull();
        assertThat(actualUrl.url()).endsWith(shortUrlKey);
    }

    @DisplayName("새로운 단축된 url을 생성한 후 반환한다.")
    @Test
    void testGetNewShortUrl() {
        // given
        String longUrl = "https://www.naver.com";
        UrlRequest urlRequest = new UrlRequest(longUrl);

        // when
        UrlResponse actualUrl = urlService.getShortUrl(urlRequest);

        // then
        Optional<Url> expectedUrl = urlRepository.findByLongUrl(longUrl);
        String shortUrlKey = Base62.encode(expectedUrl.get().getId());
        assertThat(actualUrl).isNotNull();
        assertThat(actualUrl.url()).endsWith(shortUrlKey);
    }

    @DisplayName("단축된 url을 원래 url로 반환한다.")
    @Test
    void testGetLongUrl() {
        // given
        String longUrl = "https://www.naver.com";
        String path = getShortKey(longUrl);

        // when
        String actualLongUrl = urlService.getLongUrl(path);

        // then
        assertThat(actualLongUrl).isEqualTo(longUrl);
    }

    private String getShortKey(String longUrl) {
        UrlRequest request = new UrlRequest(longUrl);
        String[] split = urlService.getShortUrl(request).url().split("/");

        return split[split.length - 1];
    }
}
