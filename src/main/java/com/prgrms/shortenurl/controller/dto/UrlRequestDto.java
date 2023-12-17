package com.prgrms.shortenurl.controller.dto;

import lombok.Builder;

@Builder
public record UrlRequestDto(String originUrl, String encodingType) {
}
