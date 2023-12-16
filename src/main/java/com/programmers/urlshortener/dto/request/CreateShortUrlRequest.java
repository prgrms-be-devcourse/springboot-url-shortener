package com.programmers.urlshortener.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record CreateShortUrlRequest(@NotBlank @URL String url) {
}
