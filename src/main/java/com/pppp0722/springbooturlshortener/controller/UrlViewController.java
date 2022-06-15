package com.pppp0722.springbooturlshortener.controller;

import com.pppp0722.springbooturlshortener.domain.UrlRequestDto;
import com.pppp0722.springbooturlshortener.domain.UrlResponseDto;
import com.pppp0722.springbooturlshortener.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UrlViewController {

    private final UrlService urlService;

    public UrlViewController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @PostMapping("/")
    public String getShortUrl(UrlRequestDto urlRequestDto, Model model) {
        UrlResponseDto urlResponseDto = urlService.createUrl(urlRequestDto);
        model.addAttribute("urlResponseDto", urlResponseDto);

        return "result";
    }

    @GetMapping("/{shortId}")
    public String redirectOriginalUrl(@PathVariable String shortId) {
        String originalUrl = urlService.getOriginalUrl(shortId);

        return "redirect:" + originalUrl;
    }
}
