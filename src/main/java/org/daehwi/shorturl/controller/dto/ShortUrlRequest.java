package org.daehwi.shorturl.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ShortUrlRequest(
        @NotBlank(message = "url must not be blank")
        String url
) {
}
