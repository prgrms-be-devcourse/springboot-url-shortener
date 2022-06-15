package com.su.urlshortener.url.dto;

import com.su.urlshortener.url.entity.Url;
import com.su.urlshortener.url.service.shortener.ShorteningAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ResponseUrlDto {

    private Long id;

    private ShorteningAlgorithm algorithm;

    private String originUrl;

    private String shotToken;

    private long visitNum;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ResponseUrlDto from(Url url) {
        return new ResponseUrlDto(url.getId(), url.getAlgorithm(), url.getOriginUrl(), url.getShotToken(), url.getVisitNum(), url.getCreatedAt(), url.getUpdatedAt());
    }
}
