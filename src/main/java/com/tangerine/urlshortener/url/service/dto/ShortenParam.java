package com.tangerine.urlshortener.url.service.dto;

import com.tangerine.urlshortener.url.model.vo.Algorithm;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;

public record ShortenParam(
        OriginUrl originUrl,
        Algorithm algorithm
) {

}
