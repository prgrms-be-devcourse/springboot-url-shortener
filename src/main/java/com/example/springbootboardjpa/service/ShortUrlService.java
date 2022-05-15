package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.converter.ShortUrlConverter;
import com.example.springbootboardjpa.domain.ShortUrl;
import com.example.springbootboardjpa.domain.Url;
import com.example.springbootboardjpa.dto.CreateShortUrlDto;
import com.example.springbootboardjpa.dto.ShortUrlDto;
import com.example.springbootboardjpa.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ShortUrlService {
    @Autowired
    private ShortUrlRepository urlRepository;

    @Transactional
    public ShortUrlDto create(CreateShortUrlDto createUrlDto) {
        Assert.isTrue(Url.checkUrl(createUrlDto.getUrl()), "Invalid email address");
        ShortUrl result = urlRepository.findByUrl(createUrlDto.getUrl()).orElse(null);
        if (result == null) {
            result = urlRepository.save(ShortUrlConverter.toUrl(createUrlDto));
        }
        return ShortUrlConverter.toUrlDto(result);
    }

    @Transactional
    public ShortUrlDto read(String shortId) {
        ShortUrl entity = urlRepository.findByShortId(shortId).orElseThrow(()-> new RuntimeException("Short id not exist"));
        entity.setCount();
        return ShortUrlConverter.toUrlDto(entity);
    }
}
