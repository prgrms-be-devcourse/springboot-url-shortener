package com.springboot.springbooturlshortner.controller;

import com.springboot.springbooturlshortner.service.ShortenUrlRequestDto;
import com.springboot.springbooturlshortner.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String showDefaultPage() {
        return "shortenUrl";
    }

    @PostMapping("/shorten-url")
    public String shortenUrl(@ModelAttribute ShortenUrlRequestDto shortenUrlRequestDto, Model model) {
        String shortenUrl = urlService.createShortenUrl(shortenUrlRequestDto);
        model.addAttribute("shortenUrl", shortenUrl);

        return "shortenUrl";
    }

    @GetMapping("/jeurl.com/{uniqueKey}")
    public void redirectToOriginUrl(@PathVariable String uniqueKey, HttpServletResponse response) throws IOException {
        String originUrl = urlService.getOriginUrl(uniqueKey);

        response.sendRedirect(originUrl);
    }
}
