package com.prgrms.wonu606.shorturl.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ShortenUrlCreateRequest(
        @NotBlank(message = "URL은 null이거나 공백일 수 없습니다.")
        String originalUrl) {

}
