package com.prgrms.wonu606.shorturl.controller.mapper;

import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateRequest;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateParam;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UrlShortenerApiParamMapper {

    ShortenUrlCreateParam toShortenUrlCreateParam(ShortenUrlCreateRequest request);
}
