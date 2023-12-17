package org.prgms.springbooturlshortener.domain.shorturl.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.prgms.springbooturlshortener.domain.shorturl.service.ShortUrlService;
import org.prgms.springbooturlshortener.domain.shorturl.service.dto.TransformRequestUrlDto;
import org.prgms.springbooturlshortener.domain.shorturl.service.dto.TransformedShortUrlDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/")
    public String showMain() {
        return "index";
    }

    @GetMapping("/{transformedUrl}")
    public String redirectToOriginalUrl(@PathVariable String transformedUrl) {
        String originalUrl = shortUrlService.getOriginalUrl(transformedUrl);

        return "redirect:https://" + originalUrl;
    }

    @PostMapping("/shorten")
    public String transformOriginalUrl(TransformRequestUrlDto transformRequestUrlDto, RedirectAttributes redirectAttributes) {
        log.info(transformRequestUrlDto.originalUrl());

        TransformedShortUrlDto transformedShortUrlDto = shortUrlService.saveOriginalUrl(transformRequestUrlDto.originalUrl());

        redirectAttributes.addFlashAttribute("transformedShortUrlDto", transformedShortUrlDto);

        return "redirect:/shorten";
    }

    @GetMapping("/shorten")
    public String showTransformedUrl(TransformedShortUrlDto transformedShortUrlDto, Model model, HttpServletRequest request) {
        log.info(transformedShortUrlDto.toString());

        model.addAttribute("transformedShortUrlDto", transformedShortUrlDto);
        model.addAttribute("domain", request.getServerName() + ":" + request.getServerPort());

        return "short-url-info";
    }
}
