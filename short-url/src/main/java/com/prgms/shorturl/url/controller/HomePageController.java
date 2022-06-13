package com.prgms.shorturl.url.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
