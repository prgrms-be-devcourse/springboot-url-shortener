package com.programmers.urlshortener.url.dto.request;

import com.programmers.urlshortener.url.domain.Algorithm;
import com.programmers.urlshortener.url.domain.Url;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortUrlCreateRequest {

    @Size(min = 20, message = "url의 길이는 20글자를 넘어야만 합니다.")
    @NotBlank(message = "문자열은 빈 값이거나 공백이 포함될 수 없습니다.")
    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)", message = "입력하신 문자열은 url이 아닙니다.")
    private String originalUrl;

    @NotNull(message = "알고리즘의 값은 빈 값일 수 없습니다.")
    private Algorithm algorithm;

    @Builder
    public ShortUrlCreateRequest(String originalUrl, Algorithm algorithm) {
        this.originalUrl = originalUrl;
        this.algorithm = algorithm;
    }

    public Url toEntity(String clientIp) {
        return Url.builder()
            .originalUrl(originalUrl)
            .algorithm(algorithm)
            .ip(clientIp)
            .build();
    }
}
