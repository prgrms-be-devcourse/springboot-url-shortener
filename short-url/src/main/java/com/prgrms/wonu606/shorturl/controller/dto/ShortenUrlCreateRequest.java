package com.prgrms.wonu606.shorturl.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ShortenUrlCreateRequest(
        @NotBlank(message = "URL은 null이거나 공백일 수 없습니다.")
        @Length(max = 2000, message = "URL 길이는 2000자를 넘길 수 없습니다.")
        String originalUrl) {

}
