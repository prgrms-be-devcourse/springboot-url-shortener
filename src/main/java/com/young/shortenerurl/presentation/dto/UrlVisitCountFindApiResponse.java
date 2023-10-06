package com.young.shortenerurl.presentation.dto;

import com.young.shortenerurl.application.dto.UrlVisitCountFindResponse;

public record UrlVisitCountFindApiResponse(
        String originUrl,
        String encodedUrl,
        Long visitCount) {
    public static UrlVisitCountFindApiResponse of(UrlVisitCountFindResponse response, String urlPreFix) {
        return new UrlVisitCountFindApiResponse(
                response.originUrl(),
                urlPreFix + response.encodedUrl(),
                response.visitCount());
    }
}
