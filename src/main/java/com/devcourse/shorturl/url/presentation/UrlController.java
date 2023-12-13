package com.devcourse.shorturl.url.presentation;

import com.devcourse.shorturl.url.application.UrlService;
import com.devcourse.shorturl.url.dto.CreateUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/url")
public class UrlController {
    private final UrlService urlService;

    @GetMapping
    public String showForm() {
        return "shortenUrlForm";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("originalUrl") String originalUrl, RedirectAttributes redirectAttributes) {
        CreateUrlResponse response = urlService.createShortUrl(originalUrl);
        redirectAttributes.addAttribute("originalUrl", originalUrl);
        redirectAttributes.addAttribute("shortenedUrl", response.shortUrl());
        redirectAttributes.addAttribute("hits", response.hits());

        return "redirect:/url/shortened";
    }
    @GetMapping("/shortened")
    public String shortenedUrlPage(@RequestParam("originalUrl") String originalUrl,
                             @RequestParam("shortenedUrl") String shortenedUrl,
                             @RequestParam("hits") String hits,
                             Model model){
        model.addAttribute("originalUrl", originalUrl);
        model.addAttribute("shortenedUrl", shortenedUrl);
        model.addAttribute("hits", hits);
        return "shortenedUrlResult";
    }

    @GetMapping("/{shortUrl}")
    public String redirectLongUrl(@PathVariable String shortUrl) {
        String longUrl = urlService.redirectLongUrl(shortUrl);
        return "redirect:" + longUrl;
    }

}
