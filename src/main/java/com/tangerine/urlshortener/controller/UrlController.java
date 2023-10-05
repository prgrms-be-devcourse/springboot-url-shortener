package com.tangerine.urlshortener.controller;

import com.tangerine.urlshortener.model.vo.OriginUrl;
import com.tangerine.urlshortener.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
