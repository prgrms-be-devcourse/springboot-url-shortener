package com.tangerine.urlshortener.service;

import com.tangerine.urlshortener.controller.UrlMappingResult;
import com.tangerine.urlshortener.model.UrlMapping;
import com.tangerine.urlshortener.model.vo.OriginUrl;
import com.tangerine.urlshortener.repository.UrlMappingJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UrlService {

    private final UrlMappingJpaRepository urlMappingJpaRepository;

    public UrlService(UrlMappingJpaRepository urlMappingJpaRepository) {
        this.urlMappingJpaRepository = urlMappingJpaRepository;
    }

    public UrlMappingResult findMapping(OriginUrl originUrl) {
        UrlMapping urlMapping = urlMappingJpaRepository.findByOriginUrl(originUrl)
                .orElseThrow(() -> new RuntimeException("해당 URL은 매핑되지 않았습니다."));
        return UrlMappingResult.of(urlMapping);
    }
}
