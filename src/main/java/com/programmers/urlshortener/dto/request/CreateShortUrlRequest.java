package com.programmers.urlshortener.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlRequest(@NotBlank String url) {
}
