package com.taehan.urlshortener.dto;

import com.taehan.urlshortener.model.AlgorithmType;

import javax.validation.constraints.Pattern;
import java.util.Objects;

public class UrlRequestDto {

    @Pattern(regexp="[(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
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
        return url.equals(that.url);
    }
}
