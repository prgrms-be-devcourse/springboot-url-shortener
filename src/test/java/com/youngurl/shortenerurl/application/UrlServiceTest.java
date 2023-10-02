package com.youngurl.shortenerurl.application;

import com.youngurl.shortenerurl.application.dto.UrlCreateRequest;
import com.youngurl.shortenerurl.infrastructures.UrlJpaRepository;
import com.youngurl.shortenerurl.model.EncodingType;
import com.youngurl.shortenerurl.model.Url;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Transactional
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlJpaRepository urlJpaRepository;

    private Url setupUrl;

    @BeforeEach
    void setup(){
        Url url = new Url("setup-url1", EncodingType.BASE_62);
        setupUrl = urlJpaRepository.save(url);
        setupUrl.encode();
    }

    @Test
    @DisplayName("originUrl과 EncodingType을 통해 인코딩된 url을 생성할 수 있다.")
    void createUrl() {
        //given
        UrlCreateRequest request = new UrlCreateRequest("test-orgin-url", EncodingType.BASE_62);

        //when
        String encodedUrl = urlService.createUrl(request);

        //then
        Url url = urlJpaRepository.findByEncodedUrl(encodedUrl).get();
        assertThat(url.getOriginUrl()).isEqualTo("test-orgin-url");
    }

    @Test
    @DisplayName("인코딩된 단축 URL을 통해 원본 URL을 조회할 수 있다.")
    void findOriginUrl() {
        //given
        String savedEncodedUrl = setupUrl.getEncodedUrl();
        String savedOriginUrl = setupUrl.getOriginUrl();

        //when
        String originUrl = urlService.findOriginUrl(savedEncodedUrl);

        //then
        assertThat(originUrl).isEqualTo(savedOriginUrl);
    }

}