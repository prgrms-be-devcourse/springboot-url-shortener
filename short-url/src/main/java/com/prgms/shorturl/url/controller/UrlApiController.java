package com.prgms.shorturl.url.controller;

import com.prgms.shorturl.common.dto.ApiResponse;
import com.prgms.shorturl.url.dto.UrlRequestDto;
import com.prgms.shorturl.url.dto.UrlResponseDto;
import com.prgms.shorturl.url.service.UrlService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UrlApiController {

    private final UrlService urlService;

    @PostMapping("/urls")
    public ApiResponse<UrlResponseDto> shortenUrl(@RequestBody @Valid UrlRequestDto urlRequestDto) {
        String shortUrl = urlService.shortenUrl(urlRequestDto.getLongUrl());
        UrlResponseDto urlResponseDto = new UrlResponseDto(urlRequestDto.getLongUrl(), shortUrl);
        log.info("shorten Url {}", shortUrl);
        return ApiResponse.ok(urlResponseDto);
    }

    @GetMapping("/{shortUrl}")
    public void redirectUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String longUrl = urlService.getLongUrlByShortUrl(shortUrl);
        log.info("{} redirect to {}", shortUrl, longUrl);
        response.sendRedirect(longUrl);
    }

}
