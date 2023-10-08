package com.example.springbooturlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    @GetMapping("/index")
    public String entry() {
        return "index";
    }

}
