package com.prgrms.url_shortener.controller;

import com.prgrms.url_shortener.dto.ShortenUrlRequest;
import com.prgrms.url_shortener.dto.ShortenUrlResponse;
import com.prgrms.url_shortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping
    public String home() {
        return "index";
    }

    @PostMapping("/url")
    public String getShortenUrl(@Valid @ModelAttribute ShortenUrlRequest request, Model model) {
        ShortenUrlResponse response = urlService.getShortUrl(request);
        model.addAttribute("shortenUrl", response.shortenUrl());
        model.addAttribute("requestCount", response.requestCount());

        return "index";
    }

    @GetMapping("/url/{shortenUrl}")
    public String redirectOriginUrl(@PathVariable String shortenUrl) {
        String originUrl = urlService.getOriginUrl(shortenUrl);
        return "redirect:" + originUrl;
    }
}
