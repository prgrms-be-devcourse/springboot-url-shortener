package com.example.shorturl.dto.request;

import com.example.shorturl.domain.Algorithm;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record ShortUrlCreateRequest(
    @URL(message = "올바른 URL 형식이 아닙니다.")
    String originUrl,

    @NotNull(message = "인코딩을 위한 알고리즘을 입력해주세요.")
    Algorithm algorithm
) {

}
