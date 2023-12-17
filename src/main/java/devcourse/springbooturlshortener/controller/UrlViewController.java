package devcourse.springbooturlshortener.controller;

import devcourse.springbooturlshortener.dto.ShortUrlCreateRequest;
import devcourse.springbooturlshortener.dto.ShortUrlFindResponse;
import devcourse.springbooturlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/url")
public class UrlViewController {

    private final UrlService urlService;

    @GetMapping
    public String getUrlInputPage() {
        return "/url-input";
    }

    @GetMapping("/short_url")
    public String getShortUrlPage(
            @RequestParam String shortUrl,
            @RequestParam long hit,
            Model model
    ) {
        model.addAttribute("shortUrl", shortUrl);
        model.addAttribute("hit", hit);

        return "/short-url";
    }

    @PostMapping("/short_url")
    public String getShortenUrl(
            @RequestParam String originUrl,
            RedirectAttributes redirectAttributes
    ) {
        ShortUrlFindResponse shortUrlInfo = this.urlService.createShortUrl(new ShortUrlCreateRequest(originUrl));
        redirectAttributes.addAttribute("shortUrl", shortUrlInfo.shortUrl());
        redirectAttributes.addAttribute("hit", shortUrlInfo.hit());

        return "redirect:/url/short_url";
    }

    @GetMapping("/{encodedUrl}")
    public String redirectToOriginUrl(@PathVariable(name = "encodedUrl") String encodedUrl) {
        String originUrl = this.urlService.getOriginalUrlAndIncreaseHit(encodedUrl);

        return "redirect:https://" + originUrl;
    }
}
