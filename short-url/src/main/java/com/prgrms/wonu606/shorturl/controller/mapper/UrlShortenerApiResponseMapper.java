package com.prgrms.wonu606.shorturl.controller.mapper;

import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateResponse;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UrlShortenerApiResponseMapper {

    @Mapping(target = "shortenUrl", expression = "java(baseUrl + result.hashedShortUrl())")
    ShortenUrlCreateResponse toShortenUrlCreateResponse(ShortenUrlCreateResult result, String baseUrl);
}
