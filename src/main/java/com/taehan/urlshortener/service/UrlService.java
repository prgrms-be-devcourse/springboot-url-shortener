package com.taehan.urlshortener.service;

import com.taehan.urlshortener.dto.UrlRequestDto;
import com.taehan.urlshortener.model.AlgorithmType;
import com.taehan.urlshortener.model.Url;
import com.taehan.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

// 요청이 들어올 때
// 1. 변환
// 2. 저장
// 3. 리턴

@Service
public class UrlService {

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public String convertToShortUrl(UrlRequestDto requestDto) {
        String shortUrl = requestDto.getAlgorithmType().convert(requestDto.getUrl());
        repository.save(new Url(requestDto.getUrl(), shortUrl, 0, requestDto.getAlgorithmType()));
        return shortUrl;
    }



}
