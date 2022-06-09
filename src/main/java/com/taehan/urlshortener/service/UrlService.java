package com.taehan.urlshortener.service;

import com.taehan.urlshortener.dto.UrlRequestDto;
import com.taehan.urlshortener.model.Url;
import com.taehan.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service
public class UrlService {

    private static final String DEFAULT_SHORT_URL_KEY = "taehan";

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    // 분리 고민
    public Long save(UrlRequestDto requestDto) {
        Url url = repository.save(new Url(requestDto.getUrl(), DEFAULT_SHORT_URL_KEY, requestDto.getAlgorithmType()));
        String shortUrl = requestDto.getAlgorithmType().convert(url.getId());
        url.changeShortUrl(shortUrl);
        return url.getId();
    }

    public String getOriginalUrl(String shortUrl) {
        Url findUrl = repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 shortUrl"));
        findUrl.addCount();
        return findUrl.getUrl();
    }

    public Url findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("저장되지 않음. Err"));
    }
}
