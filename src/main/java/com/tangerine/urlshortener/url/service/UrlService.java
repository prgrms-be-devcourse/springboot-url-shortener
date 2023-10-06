package com.tangerine.urlshortener.url.service;

import com.tangerine.urlshortener.url.model.UrlMapping;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.model.vo.RequestCount;
import com.tangerine.urlshortener.url.model.vo.ShortUrl;
import com.tangerine.urlshortener.url.repository.UrlMappingJpaRepository;
import com.tangerine.urlshortener.url.service.dto.ShortenParam;
import com.tangerine.urlshortener.url.service.dto.UrlMappingResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UrlService {

    private final UrlMappingJpaRepository urlMappingJpaRepository;

    public UrlService(UrlMappingJpaRepository urlMappingJpaRepository) {
        this.urlMappingJpaRepository = urlMappingJpaRepository;
    }

    @Transactional
    public UrlMappingResult createShortUrl(ShortenParam shortenParam) {
        UrlMapping urlMapping = urlMappingJpaRepository.findByOriginUrl(shortenParam.originUrl())
                .orElseGet(() -> urlMappingJpaRepository.save(
                        new UrlMapping(
                                shortenParam.originUrl(),
                                new ShortUrl(),
                                shortenParam.algorithm(),
                                new RequestCount(0)
                        )
                ));
        if (urlMapping.getShortUrl().isEmptyShortUrl()) {
            String encoded = shortenParam.algorithm().encode(urlMapping.getId());
            urlMapping.setShortUrl(new ShortUrl(encoded));   
        }
        return UrlMappingResult.of(urlMapping);
    }

    public UrlMappingResult findMapping(OriginUrl originUrl) {
        UrlMapping urlMapping = urlMappingJpaRepository.findByOriginUrl(originUrl)
                .orElseThrow(() -> new RuntimeException("해당 URL은 매핑되지 않았습니다."));
        return UrlMappingResult.of(urlMapping);
    }

}
