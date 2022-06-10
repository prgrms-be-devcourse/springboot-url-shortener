package com.taehan.urlshortener.service;

import com.taehan.urlshortener.dto.UrlRequestDto;
import com.taehan.urlshortener.model.Url;
import com.taehan.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
public class UrlService {

    private static final String DEFAULT_SHORT_URL_KEY = "taehan";
    private static final String WRONG_SHORT_URL_ERR_MSG = "저장되지 않은 shortUrl";
    private static final String FIND_BY_ID_ERR_MSG = "존재하지 않는 Url Id";

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public Long save(UrlRequestDto requestDto) {
        Url url = repository.save(new Url(requestDto.getUrl(), DEFAULT_SHORT_URL_KEY, requestDto.getAlgorithmType()));
        String shortUrl = requestDto.getAlgorithmType().convert(url.getId());
        url.changeShortUrl(shortUrl);
        return url.getId();
    }

    @Transactional(readOnly = true)
    public String getOriginalUrl(String shortUrl) {
        Url findUrl = repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalArgumentException(WRONG_SHORT_URL_ERR_MSG));
        findUrl.addCount();
        return findUrl.getOriginalUrl();
    }

    @Transactional(readOnly = true)
    public Url findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(FIND_BY_ID_ERR_MSG));
    }
}
