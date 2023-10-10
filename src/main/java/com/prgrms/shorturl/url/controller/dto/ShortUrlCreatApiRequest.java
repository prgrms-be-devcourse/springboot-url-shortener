package com.prgrms.shorturl.url.controller.dto;

import com.prgrms.shorturl.url.controller.validation.HttpUrl;
import jakarta.validation.constraints.NotBlank;

public record ShortUrlCreatApiRequest(
        @HttpUrl @NotBlank(message = "origin url은 null이거나 빈 값일 수 없습니다.") String originUrl,
        @NotBlank(message = "인코딩 방법은 null이거나 빈 값일 수 없습니다.") String strategyType
) {
}
