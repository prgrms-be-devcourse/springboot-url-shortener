package com.young.shortenerurl.url.handler.dto;

public record OriginUrlFindEvent(
        Long urlId
) {
    public static OriginUrlFindEvent from(Long urlId) {
        return new OriginUrlFindEvent(urlId);
    }
}
