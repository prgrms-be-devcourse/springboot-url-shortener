package com.tangerine.urlshortener.url.service;

import com.tangerine.urlshortener.url.model.UrlMapping;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.model.vo.RequestCount;
import com.tangerine.urlshortener.url.model.vo.ShortUrl;
import com.tangerine.urlshortener.url.repository.UrlMappingJpaRepository;
import com.tangerine.urlshortener.url.service.dto.ShortenParam;
import com.tangerine.urlshortener.url.service.dto.UrlMappingResult;
import com.tangerine.urlshortener.url.service.dto.UrlMappingResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public OriginUrl findOriginUrl(ShortUrl shortUrl) {
        UrlMapping urlMapping = urlMappingJpaRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("해당 URL은 매핑되지 않았습니다."));
        urlMapping.addRequestCount();
        return urlMapping.getOriginUrl();
    }

    public UrlMappingResults findAllMappings(Pageable pageable) {
        Page<UrlMapping> mappings = urlMappingJpaRepository.findAll(pageable);
        return UrlMappingResults.of(mappings);
    }

}
