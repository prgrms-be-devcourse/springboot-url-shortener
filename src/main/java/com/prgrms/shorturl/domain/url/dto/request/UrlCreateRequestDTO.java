package com.prgrms.shorturl.domain.url.dto.request;

import com.prgrms.shorturl.algorithm.AlgorithmType;
import com.prgrms.shorturl.domain.url.entity.Url;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
public record UrlCreateRequestDTO(
        @URL(message = "잘못된 URL 형식입니다.")
        @NotBlank(message = "URL을 입력해주세요.")
        @Length(max = 255, message = "URL 길이는 {max} 이하의 URL만 가능합니다.")
        String originUrl,

        @NotNull(message = "알고리즘 타입을 선택해주세요.")
        AlgorithmType algorithmType
) {
    public Url toEntity() {
        return Url.builder()
                .originUrl(originUrl)
                .algorithmType((algorithmType))
                .build();
    }
}
