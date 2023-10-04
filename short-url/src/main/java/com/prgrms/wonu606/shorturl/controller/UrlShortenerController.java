package com.prgrms.wonu606.shorturl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlShortenerController {

    @GetMapping("/")
    public String displayIndexPage() {
        return "index";
    }
}
