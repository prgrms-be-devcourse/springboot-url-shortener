package org.programmers.springbooturlshortener;

import lombok.RequiredArgsConstructor;
import org.programmers.springbooturlshortener.encoding.Encoding;
import org.programmers.springbooturlshortener.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UrlService urlService;

    @GetMapping("/")
    public String mainPage(Model model) {
        UrlCreateCommandObject url = new UrlCreateCommandObject();
        model.addAttribute("url", url);

        Encoding[] encodings = Encoding.values();
        model.addAttribute("encodings", encodings);

        return "main";
    }

    @PostMapping("/")
    public String createCommand(@ModelAttribute UrlCreateCommandObject url, RedirectAttributes redirectAttributes) {
        String shortenUrl = urlService.newUrl(url.getOriginal(), url.getEncoding());
        redirectAttributes.addFlashAttribute("shortenUrl", shortenUrl);
        return "redirect:";
    }

    @GetMapping("/{shortenUrl}")
    public String redirectToOriginalUrl(@PathVariable String shortenUrl) {
        String originalUrl = urlService.getOriginalUrl(shortenUrl);
        return "redirect:" + originalUrl;
    }
}