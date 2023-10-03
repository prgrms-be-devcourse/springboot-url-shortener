package com.example.demo.controller;

import com.example.demo.dto.UrlRequestDTO;
import com.example.demo.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/dev.choi")
public class ShortUrlController {
    private final UrlService urlService;

    @GetMapping("/createForm")
    public String createForm() {
        return "short/createShortUrlForm";
    }

    @PostMapping
    public String saveUrl(
            UrlRequestDTO urlRequestDTO,
            Model model
    ) {
        model.addAttribute("urlResponseDTO", urlService.saveUrl(urlRequestDTO));

        return "short/urlInfo";
    }

    @GetMapping("/{encoded}")
    public String redirect(@PathVariable String encoded) {
        String orgUrl = urlService.redirectUrl(encoded);

        return "redirect:" + orgUrl;
    }
}
