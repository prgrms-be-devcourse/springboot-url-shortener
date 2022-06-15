package com.su.urlshortener.url.service;

import com.su.urlshortener.url.exception.UrlNotFoundException;
import com.su.urlshortener.url.dto.RequestUrlDto;
import com.su.urlshortener.url.dto.ResponseUrlDto;
import com.su.urlshortener.url.entity.Url;
import com.su.urlshortener.url.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public ResponseUrlDto makeShortUrl(RequestUrlDto dto) {
        var generatedToken = dto.getAlgorithm().makeShorteningKey(dto.getOriginUrl());
        var savedUrl = urlRepository.save(Url.of(dto.getOriginUrl(), generatedToken, dto.getAlgorithm()));
        return ResponseUrlDto.from(savedUrl);
    }

    //for redirect
    @Transactional
    public String findOriginUrlByToken(String shotToken) {
        var url = urlRepository.findByShotToken(shotToken)
                .orElseThrow(() -> new UrlNotFoundException("오리지널 URL을 찾을 수 없습니다."));
        url.visitUrl();
        return url.getOriginUrl();
    }

    // for url detail
    public ResponseUrlDto findUrlDtoByToken(String shotToken) {
        var findUrl = urlRepository.findByShotToken(shotToken)
                .orElseThrow(() -> new UrlNotFoundException("일치하는 Token 이 없습니다."));
        return ResponseUrlDto.from(findUrl);
    }
}
