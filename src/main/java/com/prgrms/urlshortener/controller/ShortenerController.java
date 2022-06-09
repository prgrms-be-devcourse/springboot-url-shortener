package com.prgrms.urlshortener.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/short-url")
public class ShortenerController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    // get 화면
    @GetMapping
    public String viewShortenerPage() {
        return "/index";
    }

    // post
    @PostMapping
    public String makeShortenUrl(@RequestParam @NotBlank String url) {
        logger.info("success:" + url);

        //

        return "redirect:/";
    }

    // shortener result

    // redirect
}
