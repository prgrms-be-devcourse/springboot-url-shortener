package prgrms.project.shorturl.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import prgrms.project.shorturl.domain.ShortUrlService;
import prgrms.project.shorturl.dto.ShortUrlCreateRequest;
import prgrms.project.shorturl.dto.ShortUrlResponse;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ShortUrlService.class})
class ShortUrlServiceTest {

    @Autowired
    ShortUrlService shortUrlService;

    ShortUrlCreateRequest createRequest = new ShortUrlCreateRequest("https://google.com", "BASE62");

    @Test
    @DisplayName("알고리즘에 맞는 ShortUrl 을 생성한다.")
    void testCreateShortUrl() {
        var shortUrlResponse = createShortUrlResponse(createRequest);

        assertThat(shortUrlResponse.shortUrl()).isNotNull();
        assertThat(shortUrlResponse.originUrl()).isEqualTo(createRequest.originUrl());
        assertThat(shortUrlResponse.requestCount()).isEqualTo(0L);
    }

    @Test
    @DisplayName("ShortUrl 을 요청하면 요청 횟수를 증가시키고 리다이렉트할 수 있도록 원본 주소와 요청 횟수를 반환한다.")
    void testIncreaseRequestCount() {
        var shortUrlResponse = createShortUrlResponse(createRequest);
        var countOfBeforeRequest = shortUrlResponse.requestCount();

        var shortUrlRedirectResponse = shortUrlService.increaseRequestCount(shortUrlResponse.shortUrl());
        var countOfAfterRequest = shortUrlRedirectResponse.requestCount();

        assertThat(shortUrlRedirectResponse.originUrl()).isNotNull();
        assertThat(shortUrlRedirectResponse.originUrl()).isEqualTo(shortUrlRedirectResponse.originUrl());
        assertThat(countOfAfterRequest).isGreaterThan(countOfBeforeRequest);
    }

    private ShortUrlResponse createShortUrlResponse(ShortUrlCreateRequest createRequest) {
        return shortUrlService.createShortUrl(createRequest);
    }
}
