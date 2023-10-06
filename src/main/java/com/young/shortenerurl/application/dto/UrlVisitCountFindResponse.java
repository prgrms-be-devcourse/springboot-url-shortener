package com.young.shortenerurl.application.dto;

import com.young.shortenerurl.model.Url;

public record UrlVisitCountFindResponse(
        String originUrl,
        String encodedUrl,
        Long visitCount) {
    public static UrlVisitCountFindResponse from(Url url) {
        return new UrlVisitCountFindResponse(
                url.getOriginUrl(),
                url.getEncodedUrl(),
                url.getVisitCount());
    }
}
