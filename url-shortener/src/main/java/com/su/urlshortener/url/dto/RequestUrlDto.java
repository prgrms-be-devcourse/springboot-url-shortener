package com.su.urlshortener.url.dto;

import com.su.urlshortener.url.service.shortener.ShorteningAlgorithm;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class RequestUrlDto {

    @NotEmpty
    ShorteningAlgorithm algorithm;

    @URL
    String originUrl;

    private RequestUrlDto(ShorteningAlgorithm algorithm, String originUrl) {
        this.algorithm = algorithm;
        this.originUrl = originUrl;
    }

    public static RequestUrlDto of(ShorteningAlgorithm algorithm, String originUrl) {
        return new RequestUrlDto(algorithm, originUrl);
    }
}
