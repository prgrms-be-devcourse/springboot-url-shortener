package com.taehan.urlshortener.dto;

import com.taehan.urlshortener.model.AlgorithmType;
import javax.validation.constraints.Pattern;

import static com.taehan.urlshortener.model.RegexPattern.ORIGINAL_URL;


public class UrlRequestDto {
    @Pattern(regexp=ORIGINAL_URL)
    private String url;

    private AlgorithmType algorithmType;

    public UrlRequestDto(String url, AlgorithmType algorithmType) {
        this.url = url;
        this.algorithmType = algorithmType;
    }

    public String getUrl() {
        return url;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlRequestDto that = (UrlRequestDto) o;
        return url.equals(that.url) && algorithmType == that.algorithmType;
    }
}
