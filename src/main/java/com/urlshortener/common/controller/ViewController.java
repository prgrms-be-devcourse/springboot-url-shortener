package com.urlshortener.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {
    @GetMapping
    public String main() {
        return "index";
    }
}
