package com.prgrms.shorturl.url.repository;

import com.prgrms.shorturl.Fixtures;
import com.prgrms.shorturl.url.model.Urls;
import com.prgrms.shorturl.url.service.ShortUrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ShortUrlRepositoryImplTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ShortUrlRepositoryImpl shortUrlRepository;

    @Autowired
    private ShortUrlService shortUrlService;

    private Urls savedNaverUrl;

    @BeforeEach
    void setUp() {
        Urls naverUrlToSave = Fixtures.naverUrlInOriginUrl();
        shortUrlService.creatShortUrl(naverUrlToSave.getOriginUrl(),"BASE62");
        savedNaverUrl = shortUrlRepository.findByOriginUrl(naverUrlToSave.getOriginUrl()).get();

        for(int i=0;i<20;i++) {
            shortUrlService.getOriginUrl(savedNaverUrl.getShortUrl());
        }

    }

    private Optional<Urls> getCacheUrl(String shortUrl){
        return Optional.ofNullable(cacheManager.getCache("urls")).map(c->c.get(shortUrl,Urls.class));
    }

    @Test
    @DisplayName("요청 횟수가 정해진 limit 수를 넘어가면 2차 캐시에 저장하여 다음 요청에서 2차 캐시에 있는 origin url을 반환한다.")
    void getUrlByShortUrl_overLimitedBoundCount_returnCache() {
        //when
        Urls urlByShortUrl = shortUrlRepository.getUrlByShortUrl(savedNaverUrl.getShortUrl());

        //then
        assertThat(urlByShortUrl).isEqualTo(getCacheUrl(savedNaverUrl.getShortUrl()).get());
    }

}
