package com.young.shortenerurl.url.application;

import com.young.shortenerurl.url.application.dto.UrlCreateRequest;
import com.young.shortenerurl.url.application.dto.UrlInfoFindResponse;
import com.young.shortenerurl.url.infrastructures.UrlJpaRepository;
import com.young.shortenerurl.url.model.EncodedUrl;
import com.young.shortenerurl.url.model.EncodingType;
import com.young.shortenerurl.url.model.Url;
import com.young.shortenerurl.url.util.Encoder;
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
        Encoder encoder = EncodingType.BASE_64_V2.getEncoder();
        String originUrl = "setup-url1";

        Url url = new Url(
                originUrl,
                new EncodedUrl(encoder.encode(5), EncodingType.BASE_64_V2)
        );

        setupUrl = urlJpaRepository.save(url);
    }

    @Test
    @DisplayName("originUrl과 Base64V2을 통해 인코딩된 url을 생성할 수 있다.")
    void createUrl_Base64V2() {
        //given
        UrlCreateRequest request = new UrlCreateRequest("test-orgin-url", EncodingType.BASE_64_V2);

        //when
        String encodedUrl = urlService.createUrl(request);

        //then
        Url url = urlJpaRepository.findByEncodedUrl(encodedUrl).get();
        assertThat(url.getOriginUrl()).isEqualTo("test-orgin-url");
    }

    @Test
    @DisplayName("originUrl과 Base64V1을 통해 인코딩된 url을 생성할 수 있다.")
    void createUrl_Base64V1() {
        //given
        UrlCreateRequest request = new UrlCreateRequest("test-orgin-url", EncodingType.BASE_64_V1);

        //when
        String encodedUrl = urlService.createUrl(request);

        //then
        Url url = urlJpaRepository.findByEncodedUrl(encodedUrl).get();
        assertThat(url.getOriginUrl()).isEqualTo("test-orgin-url");
    }

    @Test
    @DisplayName("url 저장 시 동일한 originUrl이 이미 존재한다면 존재하는 Url의 인코딩 url을 반환한다.")
    void createUrl_alreadyExistUrl(){
        //given
        UrlCreateRequest request = new UrlCreateRequest("setup-url1", EncodingType.BASE_64_V2);

        //when
        String encodedUrl = urlService.createUrl(request);

        //then
        assertThat(encodedUrl).isEqualTo(setupUrl.getEncodedUrl());
    }

    @Test
    @DisplayName("인코딩된 단축 URL을 통해 원본 URL을 조회할 수 있으며 조회수는 1씩 증가한다.")
    void findOriginUrl() {
        //given
        String savedEncodedUrl = setupUrl.getEncodedUrl();
        String savedOriginUrl = setupUrl.getOriginUrl();

        //when
        String originUrl = urlService.findOriginUrl(savedEncodedUrl);

        //then
        Url url = urlJpaRepository.findByEncodedUrl(savedEncodedUrl).get();

        assertThat(originUrl).isEqualTo(savedOriginUrl);
        assertThat(url.getVisitCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("인코딩된 단축 URL을 통해 해당 URL의 방문 수를 확인할 수 있다.")
    void findUrlVisitCount(){
        //given
        urlService.findOriginUrl(setupUrl.getEncodedUrl());
        urlService.findOriginUrl(setupUrl.getEncodedUrl());

        //when
        UrlInfoFindResponse response = urlService.findUrlInfo(setupUrl.getEncodedUrl());

        //then
        assertThat(response.originUrl()).isEqualTo(setupUrl.getOriginUrl());
        assertThat(response.encodedUrl()).isEqualTo(setupUrl.getEncodedUrl());
        assertThat(response.visitCount()).isEqualTo(2);
    }

}