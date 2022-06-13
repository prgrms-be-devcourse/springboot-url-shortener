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


    // shortUrl 호출 count 를 body 로 전달
    @GetMapping("/count/{url}")
    public ApiResponse<UrlDto.Response> checkCountOfShortUrlVisit(
            @PathVariable String url
    ) {
        Url findByShortUrl = urlService.findByShortUrl(url);
        UrlDto.Response response = new UrlDto.Response(String.valueOf(findByShortUrl.getRequestCount()));
        return ApiResponse.ok(response);
    }

    // json body 로 shortUrl을 넘겼을때 redirect
    @PostMapping("/find")
    public void redirectByShortUrl(
            HttpServletResponse response,
            @RequestBody UrlDto.ShortUrlRequest shortUrl
    ) throws  IOException {
        Url url = urlService.findByShortUrl(shortUrl.shortUrl());

        response.sendRedirect(url.getOriginUrl());
    }

    // parameter 로 shortUrl을 넘겼을때 redirect
    @GetMapping("/{url}")
    public ResponseEntity<Void> redirectByShortUrl(
            @PathVariable String url
    ) {
        Url findByShortUrl = urlService.findByShortUrl(url);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, findByShortUrl.getOriginUrl())
                .build();
    }
}
