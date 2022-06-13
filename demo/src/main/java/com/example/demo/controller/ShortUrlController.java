package com.example.demo.controller;

import com.example.demo.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @PostMapping("/url")
    public String createShortUrl(@RequestParam(value = "url") String url, Model model) {
        String shortUrl = shortUrlService.createShortUrl(url);
        model.addAttribute("shortUrl", shortUrl);
        model.addAttribute("originUrl", url);
        return "url/shortUrlResult";
    }
}
