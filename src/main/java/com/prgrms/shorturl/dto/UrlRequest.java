package com.prgrms.shorturl.dto;

import com.prgrms.shorturl.utils.HashAlgorithm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public record UrlRequest(@NotBlank @Pattern(regexp = "^((http|https)://)?(www.)?([a-zA-Z0-9]+)\\.[a-z]+([a-zA-Z0-9.?#]+)?") String originalUrl, HashAlgorithm hashAlgorithm) {
        @Builder
        public UrlRequest {
        }
}
