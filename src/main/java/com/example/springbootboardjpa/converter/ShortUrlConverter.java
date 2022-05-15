package com.example.springbootboardjpa.converter;

import com.example.springbootboardjpa.domain.ShortUrl;
import com.example.springbootboardjpa.dto.CreateShortUrlDto;
import com.example.springbootboardjpa.dto.ShortUrlDto;
import com.example.springbootboardjpa.service.ShortUrlMaker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ShortUrlConverter {
    public static ShortUrlDto toUrlDto(ShortUrl shortUrl) {
        return new ShortUrlDto().builder()
                .shortId(shortUrl.getShortId())
                .url(shortUrl.getUrl())
                .count(shortUrl.getCount())
                .createdAt(shortUrl.getCreatedAt())
                .build();
    }

    public static ShortUrl toUrl(CreateShortUrlDto createShortUrlDto) {
        ShortUrl shortUrl = new ShortUrl();
        String shortId = ShortUrlMaker.makeShortUrl();
        shortUrl.setShortId(shortId);
        shortUrl.setUrl(createShortUrlDto.getUrl());
        shortUrl.setCreatedAt(LocalDateTime.now());
        return shortUrl;
    }
}
