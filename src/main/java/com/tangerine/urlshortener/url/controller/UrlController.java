package com.tangerine.urlshortener.url.controller;

import com.tangerine.urlshortener.url.controller.dto.PageInfo;
import com.tangerine.urlshortener.url.controller.dto.ShortenRequest;
import com.tangerine.urlshortener.url.controller.dto.UrlMappingResponse;
import com.tangerine.urlshortener.url.controller.dto.UrlMappingResponses;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.model.vo.ShortUrl;
import com.tangerine.urlshortener.url.service.UrlService;
import com.tangerine.urlshortener.url.service.dto.UrlMappingResult;
import com.tangerine.urlshortener.url.service.dto.UrlMappingResults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * URL 입력폼
     *
     * @return "index"
     */
    @GetMapping(path = "/")
    public String home() {
        return "index";
    }

    /**
     * 원본 URL을 단축 URL로 매핑
     *
     * @param shortenRequest
     * @param model
     * @return "mapping-info"
     */
    @PostMapping(path = "/shortener")
    public String shortenOriginUrl(
            @ModelAttribute ShortenRequest shortenRequest,
            Model model
    ) {
        UrlMappingResult mappingInfo = urlService.createShortUrl(ShortenRequest.to(shortenRequest));
        UrlMappingResponse mapping = UrlMappingResponse.of(mappingInfo);
        model.addAttribute("mapping", mapping);
        return "mapping-info";
    }

    /**
     * 단축된 URL 요청 시 원래 URL로 리다이렉션 및 요청 횟수 증가
     *
     * @param shortUrl
     * @return "redirect:originUrl"
     */
    @GetMapping("/{shortUrl}")
    public String redirectToOriginUrl(
            @PathVariable String shortUrl
    ) {
        OriginUrl originUrl = urlService.findOriginUrl(new ShortUrl(shortUrl));
        return "redirect:" + originUrl.getOriginUrlText();
    }

     *
     * @param model
     * @return "mapping-info"
     */
    @GetMapping(path = "/shortener")
    public String mappingInfo(
            @RequestParam String originUrl,
            Model model
    ) {
        UrlMappingResult mapping = urlService.findMapping(new OriginUrl(originUrl));
        model.addAttribute("mapping", mapping);
        return "mapping-info";
    }
}
