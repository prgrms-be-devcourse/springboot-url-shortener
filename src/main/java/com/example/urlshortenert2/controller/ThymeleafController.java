package com.example.urlshortenert2.controller;

import com.example.urlshortenert2.service.ShortenerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    private final ShortenerService shortenerService;

    public ThymeleafController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @GetMapping("/")
    public String inputForm() {
        return "index";
    }
}
