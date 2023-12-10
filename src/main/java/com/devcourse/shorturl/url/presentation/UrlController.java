package com.devcourse.shorturl.url.presentation;

import com.devcourse.shorturl.url.application.UrlService;
import com.devcourse.shorturl.url.dto.CreateUrlResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @GetMapping("/")
    public String showForm(){
        return "shortenUrlForm";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("originalUrl") String originalUrl, Model model) {
        CreateUrlResponse response = urlService.createShortUrl(originalUrl);

        model.addAttribute("originalUrl", originalUrl);
        model.addAttribute("shortenedUrl", response.shortUrl());
        model.addAttribute("hits", response.hits());

        return "shortenedUrlResult";
    }

    @GetMapping("/{shortUrl}")
    public String redirectLongUrl(@PathVariable("shortUrl") String shortUrl){
        String longUrl = urlService.redirectLongUrl(shortUrl);
        return "redirect:" + longUrl;
    }

}
