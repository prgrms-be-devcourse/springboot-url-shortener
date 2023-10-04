package com.example.kdtspringbooturlshortener.urlinfo.presentation;


import com.example.kdtspringbooturlshortener.urlinfo.application.UrlInfoService;
import com.example.kdtspringbooturlshortener.urlinfo.request.UrlInfoReq;
import com.example.kdtspringbooturlshortener.urlinfo.response.UrlInfoRes;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UrlInfoController {

    private final UrlInfoService urlInfoService;

    @PostMapping("/urls")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlInfoRes createShortUrl(@RequestBody @Valid UrlInfoReq urlInfoReq) {
        return urlInfoService.createShortUrl(urlInfoReq);
    }

    @GetMapping("/urls/{shortUrl}")
    @ResponseStatus(HttpStatus.OK)
    public UrlInfoRes getShortUrlInfo(@PathVariable String shortUrl) {
        return urlInfoService.getUrlInfo(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    @ResponseStatus(HttpStatus.OK)
    public void redirectOriginUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        response.sendRedirect(urlInfoService.getOriginalUrl(shortUrl));
    }
}
