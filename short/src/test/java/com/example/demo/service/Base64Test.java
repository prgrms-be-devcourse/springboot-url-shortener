package com.example.demo.service;

import com.example.demo.dto.UrlRequest;
import com.example.demo.dto.UrlResponse;
import com.example.demo.entity.Url;
import com.example.demo.repository.UrlRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Base64Test {
    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Value("${domain.prefix}")
    private String prefix;

    @Test
    @DisplayName("인덱스 넘버로 base64 인코딩에 의해 단축 url이 만들어진다.")
    void base64() {
        // given
        String sampleUrl = "www.sample.com";
        UrlRequest urlRequest = new UrlRequest(sampleUrl);

        Long urlId = 70L;
        String expectedEncoded = "81";
        String expectedShortUrl = prefix + expectedEncoded;

        Url url = new Url(sampleUrl);
        ReflectionTestUtils.setField(url, "id", urlId);

        when(urlRepository.save(any())).thenReturn(url);

        // when
        UrlResponse urlResponse = urlService.saveUrl(urlRequest);
        String actualUrl = urlResponse.url();

        // then
        Assertions.assertThat(actualUrl).isEqualTo(expectedShortUrl);
    }
}
