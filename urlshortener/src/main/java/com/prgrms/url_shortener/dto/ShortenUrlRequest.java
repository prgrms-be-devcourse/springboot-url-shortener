package com.prgrms.url_shortener.dto;

import com.prgrms.url_shortener.dto.valid.UrlValid;
import jakarta.validation.constraints.Size;

public record ShortenUrlRequest(
    @UrlValid
    @Size(max = 255, message = "URL은 255자 이내입니다.")
    String originUrl
){}
