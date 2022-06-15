package com.prgrms.short_url.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlDto {

    private String originUrl;

    public UrlDto() {
    }

    public UrlDto(String originUrl) {
        this.originUrl = originUrl;
    }
}
