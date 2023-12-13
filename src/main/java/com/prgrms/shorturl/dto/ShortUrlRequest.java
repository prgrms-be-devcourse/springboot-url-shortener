package com.prgrms.shorturl.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public record ShortUrlRequest(@NotNull @Pattern(regexp = "^((http|https)://)?(www.)?([a-zA-Z0-9]+)\\.[a-z]+([a-zA-Z0-9.?#]+)?") String originalUrl) {
        @Builder
        public ShortUrlRequest {
        }
}
