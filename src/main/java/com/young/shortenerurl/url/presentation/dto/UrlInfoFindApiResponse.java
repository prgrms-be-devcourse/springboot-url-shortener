package com.young.shortenerurl.url.presentation.dto;

import com.young.shortenerurl.url.application.dto.UrlInfoFindResponse;

public record UrlInfoFindApiResponse(
        String originUrl,
        String encodedUrl,
        Long visitCount) {
    public static UrlInfoFindApiResponse of(UrlInfoFindResponse response, String urlPreFix) {
        return new UrlInfoFindApiResponse(
                response.originUrl(),
                urlPreFix + response.encodedUrl(),
                response.visitCount());
    }

}
