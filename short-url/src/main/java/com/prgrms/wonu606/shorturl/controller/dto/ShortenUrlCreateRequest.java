package com.prgrms.wonu606.shorturl.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ShortenUrlCreateRequest(
        @NotNull(message = "URL은 null일 수 없습니다.")
        String originalUrl) {

}
