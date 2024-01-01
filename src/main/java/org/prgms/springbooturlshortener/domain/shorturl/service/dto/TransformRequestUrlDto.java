package org.prgms.springbooturlshortener.domain.shorturl.service.dto;

import org.prgms.springbooturlshortener.domain.shorturl.common.ShortUrlUtil;

public record TransformRequestUrlDto (String originalUrl) {
    public TransformRequestUrlDto(String originalUrl) {
        this.originalUrl = ShortUrlUtil.deleteProtocol(originalUrl);
    }
}
