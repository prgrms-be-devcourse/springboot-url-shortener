package com.prgrms.shorturl.controller;

import com.prgrms.shorturl.dto.UrlRequest;
import com.prgrms.shorturl.dto.UrlResponse;
import com.prgrms.shorturl.service.UrlService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public String home() {
        return "index";
    }

    @PostMapping
    public String shortUrlAdd(@RequestParam String url, Model model) {
        UrlResponse urlResponse = urlService.convertUrl(new UrlRequest(url));
        model.addAttribute("shorturl", urlResponse.getShortenUrl());
        return "redirect:/";
    }

    @GetMapping("/{shortUrl}")
    public String shortUrlRedirect(HttpServletResponse response, @PathVariable String shortUrl) {
        return "redirect:" + urlService.searchByShortUrl(new UrlRequest(shortUrl));
    }
}
