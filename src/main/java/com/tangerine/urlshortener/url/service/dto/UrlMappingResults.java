package com.tangerine.urlshortener.url.service.dto;

import com.tangerine.urlshortener.url.model.UrlMapping;
import org.springframework.data.domain.Page;

public record UrlMappingResults(
        Page<UrlMappingResult> results
) {

    public static UrlMappingResults of(Page<UrlMapping> entities) {
        return new UrlMappingResults(entities.map(UrlMappingResult::of));
    }

}
