package com.prgrms.wonu606.shorturl.controller.mapper;

import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateRequest;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlShortenerApiParamMapper {

    ShortenUrlCreateParam toShortenUrlCreateParam(ShortenUrlCreateRequest request);
}
