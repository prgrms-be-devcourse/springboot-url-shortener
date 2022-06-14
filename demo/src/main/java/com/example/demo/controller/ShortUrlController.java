package com.example.demo.controller;

import com.example.demo.ShortUrlViewForm;
import com.example.demo.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @PostMapping("/url")
    public String createShortUrl(@ModelAttribute("shortUrlViewForm") ShortUrlViewForm shortUrlViewForm, Model model) {
        String shortUrl = shortUrlService.createShortUrl(shortUrlViewForm.getUrl(), shortUrlViewForm.getShortenAlgorithm());
        model.addAttribute("shortUrl", shortUrl);
        model.addAttribute("originUrl", shortUrlViewForm.getUrl());
        return "url/shortUrlResult";
    }

    @GetMapping("/url/{shortUrl}")
    public RedirectView getDestination(@PathVariable("shortUrl") String shortUrl) {
        String originUrl = shortUrlService.getOriginUrl(shortUrl);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originUrl);
        return redirectView;
    }
}
