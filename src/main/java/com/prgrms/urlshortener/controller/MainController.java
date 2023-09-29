package com.prgrms.urlshortener.controller;

import com.prgrms.urlshortener.utils.URLShortenStrategyConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("strategies", URLShortenStrategyConfig.StrategyType.values());
        return "home";
    }
}