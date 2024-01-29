package com.example.demo.service;

import com.example.demo.dto.UrlRequest;
import com.example.demo.entity.Url;
import com.example.demo.repository.UrlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class UrlServiceTest {
    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @Test
    @Transactional
    @DisplayName("동시에 여러 사용자가 단축 url을 만들어도, 겹치는 url이 만들어지지 않는다.")
    void saveUrl() throws InterruptedException {
        String sampleUrl = "www.sample.com";
        UrlRequest urlRequest = new UrlRequest(sampleUrl);

        int threadCount = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    urlService.saveUrl(urlRequest);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        List<Url> urls = urlRepository.findAll();

        assertThat(urls).extracting("encoded").doesNotHaveDuplicates();
    }
}