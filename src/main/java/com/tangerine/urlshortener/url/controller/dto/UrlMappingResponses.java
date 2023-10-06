package com.tangerine.urlshortener.url.controller.dto;

import com.tangerine.urlshortener.url.service.dto.UrlMappingResults;
import org.springframework.data.domain.Page;

public record UrlMappingResponses(
        Page<UrlMappingResponse> results
) {

    public static UrlMappingResponses of(UrlMappingResults mappingResults) {
        return new UrlMappingResponses(mappingResults.results()
                .map(UrlMappingResponse::of));
    }
}
