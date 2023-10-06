package com.tangerine.urlshortener.url.controller.dto;

import com.tangerine.urlshortener.url.model.vo.Algorithm;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.service.dto.ShortenParam;

public record ShortenRequest(
        String originUrl,
        String algorithm
) {

    public static ShortenParam to(ShortenRequest request) {
        return new ShortenParam(
                new OriginUrl(request.originUrl()),
                Algorithm.valueOf(request.algorithm())
        );
    }

}
