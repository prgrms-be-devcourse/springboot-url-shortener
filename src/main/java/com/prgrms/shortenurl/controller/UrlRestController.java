package com.prgrms.shortenurl.controller;

import com.prgrms.shortenurl.controller.dto.UrlRequestDto;
import com.prgrms.shortenurl.service.UrlService;
import com.prgrms.shortenurl.domain.Url;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shortenUrl")
public class UrlRestController {
    private final UrlService urlService;

    public UrlRestController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> shortenUrl(@ModelAttribute UrlRequestDto urlRequestDto) {
        Url shortenUrl = urlService.addLink(urlRequestDto.originUrl(), urlRequestDto.encodingType());
        return ResponseEntity.ok(shortenUrl.getOriginUrl());
    }

    @GetMapping(value = "/{key}")
    @ResponseBody
    public ResponseEntity<String> getUrl(@PathVariable String key) {
        String url = urlService.getUrlByKey(key);
        return ResponseEntity.ok(url);
    }
}
