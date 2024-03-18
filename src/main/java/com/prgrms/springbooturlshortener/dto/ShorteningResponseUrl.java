package com.prgrms.springbooturlshortener.dto;

import lombok.Builder;

@Builder
public record ShorteningResponseUrl(String originalUrl, String shortUrl) {

}
