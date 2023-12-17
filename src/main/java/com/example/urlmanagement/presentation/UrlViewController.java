package com.example.urlmanagement.presentation;

import com.example.urlmanagement.application.UrlService;
import com.example.urlmanagement.dto.request.CreateShortUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UrlViewController {

    private final UrlService urlService;

    @GetMapping("/shorten/new")
    public String createShortUrlPage() {
        return "url/url-form";
    }

    @PostMapping("/shorten/new")
    public String createShortUrl(CreateShortUrlRequest createShortUrlRequest, Model model) {

        String shortUrl = urlService.createShortUrl(createShortUrlRequest);
        model.addAttribute("shortUrl", shortUrl);

        return "url/short-url-result";
    }
}
