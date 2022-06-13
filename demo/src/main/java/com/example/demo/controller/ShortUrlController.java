package com.example.demo.controller;

import com.example.demo.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/url/{shortUrl}")
    public RedirectView getDestination(@PathVariable("shortUrl") String shortUrl) {
        String originUrl = shortUrlService.getOriginUrl(shortUrl);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originUrl);
        return redirectView;
    }
}
