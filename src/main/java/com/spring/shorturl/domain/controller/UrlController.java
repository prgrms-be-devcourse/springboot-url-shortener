package com.spring.shorturl.domain.controller;

import com.spring.shorturl.domain.Converter;
import com.spring.shorturl.domain.data.Url;
import com.spring.shorturl.domain.data.UrlDto;
import com.spring.shorturl.domain.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/urls")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;
    private final Converter converter;

    @PostMapping
    public ApiResponse<UrlDto.Response> saveUrl(
            @RequestBody UrlDto.SaveRequest saveRequest
    ) {
        Long savedId = urlService.save(saveRequest);
        Url url = urlService.findById(savedId);
        UrlDto.Response response = converter.convertUrlDto(url);
        return ApiResponse.ok(response);
    }

    //////////////////////////
    // Test 를 위한 코드
    @GetMapping
    public ApiResponse<UrlDto.Response> checkCountOfUrlVisit() {
        Url url = urlService.findById(1L);
        UrlDto.Response response = new UrlDto.Response(String.valueOf(url.getRequestCount()));
        return ApiResponse.ok(response);
    }
    //////////////////////////

    @PostMapping("/find")
    public void findOriginalUrlByShortUrl(
            HttpServletResponse response,
            @RequestBody UrlDto.ShortUrlRequest shortUrl
    ) throws IOException {
        Url url = urlService.findByShortUrl(shortUrl.shortUrl());
        response.sendRedirect(url.getOriginUrl());
    }
}
