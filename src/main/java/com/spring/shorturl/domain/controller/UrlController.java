package com.spring.shorturl.domain.controller;

import com.spring.shorturl.domain.Converter;
import com.spring.shorturl.domain.data.Url;
import com.spring.shorturl.domain.data.UrlDto;
import com.spring.shorturl.domain.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;


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

    // json body 로 shortUrl을 넘겼을때 redirect
    @PostMapping("/find")
    public void findOriginalUrlByShortUrl(
            HttpServletResponse response,
            @RequestBody UrlDto.ShortUrlRequest shortUrl
    ) throws URISyntaxException, IOException {
        Url url = urlService.findByShortUrl(shortUrl.shortUrl());

        response.sendRedirect(url.getOriginUrl());
    }

    // parameter 로 shortUrl을 넘겼을때 redirect
    @GetMapping("/{url}")
    public ResponseEntity<Void> redirect(@PathVariable String url) {
        Url byShortUrl = urlService.findByShortUrl(url);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, byShortUrl.getOriginUrl())
                .build();
    }
}
