package com.prgrms.shorturl.url.service;

import com.prgrms.shorturl.Fixtures;
import com.prgrms.shorturl.url.encoder.Base62Encoder;
import com.prgrms.shorturl.url.encoder.ShortUrlStrategyConfig;
import com.prgrms.shorturl.url.exception.ExistedOriginUrlException;
import com.prgrms.shorturl.url.model.Urls;
import com.prgrms.shorturl.url.repository.ShortUrlJpaRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Import(ShortUrlStrategyConfig.class)
class ShortUrlServiceImplTest {

    private final Urls NAVERURL = Fixtures.naverUrlInOriginUrl();
    ;
    private final String STRATEGY_TYPE = "BASE62";

    @Autowired
    private ShortUrlServiceImpl shortUrlService;

    @Autowired
    private ShortUrlJpaRepository shortUrlRepository;

    @Autowired
    private Base62Encoder base62Encoder;

    @Test
    @DisplayName("새로운 Origin url을 base62 인코딩 요청하면 short Url을 반환한다.")
    void creatShortUrl_newOriginUrl_returnShortUrl() {
        //when
        String result = shortUrlService.creatShortUrl(NAVERURL.getOriginUrl(), STRATEGY_TYPE);

        Urls savedUrl = shortUrlRepository.findByOriginUrl(NAVERURL.getOriginUrl()).get();
        String expectedShortUrl = base62Encoder.encodeOriginUrl(savedUrl.getId());

        //then
        assertThat(result).isEqualTo(expectedShortUrl);
    }

    @Test
    @DisplayName("저장된 Origin url을 가지고 short url 생성을 요청하면 이미 저장된 origin url이라는 예외를 던진다.")
    void creatShortUrl_existedOriginUrl_throwExcpeiton() {
        //given
        shortUrlService.creatShortUrl(NAVERURL.getOriginUrl(), STRATEGY_TYPE);

        //when_then
        assertThrows(ExistedOriginUrlException.class, () -> shortUrlService.creatShortUrl(NAVERURL.getOriginUrl(), STRATEGY_TYPE));
    }

    @Test
    @DisplayName("short url로 origin url를 잘 찾는지 확인한다.")
    void getOriginUrl_shortUrl_equals() {
        //given
        String shortUrl = shortUrlService.creatShortUrl(NAVERURL.getOriginUrl(), STRATEGY_TYPE);

        //when
        String originUrl = shortUrlService.getOriginUrl(shortUrl);

        //when_then
        assertThat(originUrl).isEqualTo(NAVERURL.getOriginUrl());
    }

}

