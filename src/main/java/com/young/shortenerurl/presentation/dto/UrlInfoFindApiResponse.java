package com.young.shortenerurl.presentation.dto;

import com.young.shortenerurl.application.dto.UrlInfoFindResponse;

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
