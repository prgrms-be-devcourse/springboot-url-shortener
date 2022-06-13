package com.spring.shorturl.domain;

import com.spring.shorturl.domain.data.Url;
import com.spring.shorturl.domain.data.UrlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Converter {

    public UrlDto.Response convertUrlDto(Url url){
        return new UrlDto.Response(url.getShortUrl());
    }

}
