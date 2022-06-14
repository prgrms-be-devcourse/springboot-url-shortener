package com.prgrms.urlshortener.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlViewController {

    @GetMapping("/")
    public String viewUrlPage() {
        return "/home";
    }

}
