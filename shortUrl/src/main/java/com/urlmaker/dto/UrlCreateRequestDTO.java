package com.urlmaker.dto;

import com.urlmaker.url.Url;

// 처음 실행할할 때 바로 요청이 진행되는 형식으로 간다.
public record UrlCreateRequestDTO(
//        @URL
//        @NotBlank
        String originUrl,

//        @NotBlank
        String algorithm
) {

    public Url toEntity() {
        return Url.builder()
                .originUrl(originUrl)
                .algorithm(algorithm)
                .build();
    }
}
