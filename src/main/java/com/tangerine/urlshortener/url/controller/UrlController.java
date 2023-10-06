package com.tangerine.urlshortener.url.controller;

import com.tangerine.urlshortener.url.controller.dto.ShortenRequest;
import com.tangerine.urlshortener.url.controller.dto.UrlMappingResponse;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.service.UrlService;
import com.tangerine.urlshortener.url.service.dto.UrlMappingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
     * 원본 URL로 매핑 정보 조회
     *
     * @param originUrl
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
