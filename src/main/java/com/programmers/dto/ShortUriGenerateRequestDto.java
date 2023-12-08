package com.programmers.dto;

import lombok.Builder;
@Builder
public record ShortUriGenerateRequestDto(String originalUri, Long index) {
}
