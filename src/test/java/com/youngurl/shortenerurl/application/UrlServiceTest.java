package com.youngurl.shortenerurl.application;

import com.youngurl.shortenerurl.application.dto.UrlCreateRequest;
import com.youngurl.shortenerurl.model.EncodingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Test
    void createUrl() {
        //given
        UrlCreateRequest request = new UrlCreateRequest("test-orgin-url", EncodingType.BASE_62);

        //when
        String response = urlService.createUrl(request);

        //then

    }

}