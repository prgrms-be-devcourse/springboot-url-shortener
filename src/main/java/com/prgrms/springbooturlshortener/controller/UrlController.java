package com.prgrms.springbooturlshortener.controller;

import com.prgrms.springbooturlshortener.dto.ShorteningResponseUrl;
import com.prgrms.springbooturlshortener.service.UrlService;
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

    private final UrlService service;

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @PostMapping("/shorten")
    public String shorteningUrl(@RequestParam String request, Model model) {
        ShorteningResponseUrl response = service.generateShortUrl(request);
        model.addAttribute("response", response);

        return "home";
    }

    @GetMapping("/{shortUrl}")
    public String getOriginalUrl(@PathVariable String shortUrl) {
        return "redirect:https://" + service.getOriginalUrl(shortUrl);
    }
}
