package org.prgms.springbooturlshortener.domain.shorturl.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlErrorCode;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlException;
import org.prgms.springbooturlshortener.domain.shorturl.service.ShortUrlService;
import org.prgms.springbooturlshortener.domain.shorturl.service.dto.TransformRequestUrlDto;
import org.prgms.springbooturlshortener.domain.shorturl.service.dto.TransformedShortUrlDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class ShortUrlController {
    private static final int HTTP_500_ERROR_BOUND = 400;

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
        TransformedShortUrlDto transformedShortUrlDto = shortUrlService.saveOriginalUrl(transformRequestUrlDto.originalUrl());

        redirectAttributes.addFlashAttribute("transformedShortUrlDto", transformedShortUrlDto);

        return "redirect:/shorten";
    }

    @GetMapping("/shorten")
    public String showTransformedUrl(TransformedShortUrlDto transformedShortUrlDto, Model model, HttpServletRequest request) {
        if (transformedShortUrlDto.shortUrl() == null) {
            throw new UrlException(UrlErrorCode.ORIGINAL_URL_NOT_FOUND);
        }

        model.addAttribute("transformedShortUrlDto", transformedShortUrlDto);
        model.addAttribute("domain", request.getServerName() + ":" + request.getServerPort());

        return "short-url-info";
    }

    @ExceptionHandler(UrlException.class)
    public String showUrlErrorPage(UrlException ex) {
        if (ex.getUrlErrorCode() > HTTP_500_ERROR_BOUND) {
            log.warn("서버에서의 에러: {}", ex.getMessage());

            return "error/500";
        }

        log.info("클라이언트에서 잘못된 입력. {}", ex.getMessage());

        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    public String showDefaultError(Exception ex) {
        log.error("예상하지 못한 에러 발생: {}", ex.getMessage());

        return "error/400";
    }
}
