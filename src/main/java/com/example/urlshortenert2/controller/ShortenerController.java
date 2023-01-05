package com.example.urlshortenert2.controller;

import com.example.urlshortenert2.service.ShortenerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShortenerController {

    private final ShortenerService shortenerService;

    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @GetMapping("/")
    public String inputForm() {
        return "index";
    }

    //TODO: Origin URL 정보를 받아서 Short URL 생성 후 DB에 저장하는 메서드 (POST)

    //TODO: Short URL 타고 들어왔을 때 Origin URL로 redirect 시키는 메서드 (GET)
}
