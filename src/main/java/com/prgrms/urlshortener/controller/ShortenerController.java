package com.prgrms.urlshortener.controller;

import com.prgrms.urlshortener.dto.ShortenUrlResponse;
import com.prgrms.urlshortener.dto.UrlResponse;
import com.prgrms.urlshortener.service.ShortenerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/short-url")
public class ShortenerController {
    private final ShortenerService shortenerService;

    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @ExceptionHandler(Exception.class)
    public String except(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "/error";
    }

    @GetMapping
    public String viewShortenerPage() {
        return "/index";
    }

    @PostMapping
    public String makeShortenUrl(@RequestParam @NotBlank String url, Model model) {
        ShortenUrlResponse shortenUrlResponse = shortenerService.createShortenUrl(url);

        model.addAttribute("encodedId", shortenUrlResponse.getEncodedId());

        return "/ShortenResult";
    }

    @GetMapping("/{encodedId}")
    public String redirectToOriginalUrl(@PathVariable @NotBlank String encodedId) {
        UrlResponse urlResponse = shortenerService.getOriginalUrl(encodedId);

        return "redirect:"+urlResponse.getUrl();
    }
}
