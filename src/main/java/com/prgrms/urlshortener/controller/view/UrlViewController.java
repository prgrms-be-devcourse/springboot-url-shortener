package com.prgrms.urlshortener.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.prgrms.urlshortener.service.UrlService;

@Controller
public class UrlViewController {

    private final UrlService urlService;

    public UrlViewController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String viewUrlPage() {
        return "/home";
    }

}
