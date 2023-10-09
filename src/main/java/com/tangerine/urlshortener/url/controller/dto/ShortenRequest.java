package com.tangerine.urlshortener.url.controller.dto;

import com.tangerine.urlshortener.url.model.vo.Algorithm;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.service.dto.ShortenParam;

public record ShortenRequest(
        String originUrl,
        Algorithm algorithm
) {

    public ShortenParam to() {
        return new ShortenParam(
                new OriginUrl(this.originUrl),
                this.algorithm
        );
    }

}
