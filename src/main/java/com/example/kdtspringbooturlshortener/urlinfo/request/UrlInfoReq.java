package com.example.kdtspringbooturlshortener.urlinfo.request;

import com.example.kdtspringbooturlshortener.urlinfo.domain.UrlInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.example.kdtspringbooturlshortener.global.common.util.RegExp.URL_PATTERN;

public record UrlInfoReq(
        @NotBlank @Pattern(regexp = URL_PATTERN) String originUrl
) {

    public UrlInfo toEntity() {
        return UrlInfo.create(originUrl, null);
    }
}
