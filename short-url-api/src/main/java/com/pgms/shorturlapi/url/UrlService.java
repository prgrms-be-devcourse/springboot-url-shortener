package com.pgms.shorturlapi.url;

import com.pgms.shorturlapi.event.UrlWatchNumberAddEvent;
import com.pgms.shorturlcoredomain.exception.NoUrlException;
import com.pgms.shorturlcoredomain.url.Url;
import com.pgms.shorturlcoredomain.url.UrlRepository;
import com.pgms.shorturlcoredomain.util.AlgorithmConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final Map<String, AlgorithmConverter> algorithmConverterMap;

    private final ApplicationEventPublisher applicationEventPublisher;

    public String getShortUrl(String url, String algorithm){
        AlgorithmConverter algorithmConverter = algorithmConverterMap.getOrDefault(algorithm, algorithmConverterMap.get("base62Converter"));

        Url findUrl = urlRepository.findUrlByOriginalUrl(url)
                .orElseGet(() -> {
                    Url createdUrl = urlRepository.save(Url.builder().originalUrl(url).build());
                    String shortUrl = algorithmConverter.encode(createdUrl.getId());
                    createdUrl.updateUrlShortUrl(shortUrl);
                    return createdUrl;
                }
        );

        return findUrl.getShortUrl();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "originalUrl", key = "#shortUrl", cacheManager = "contentCacheManager")
    public String getOriginalUrl(String shortUrl){
        applicationEventPublisher.publishEvent(new UrlWatchNumberAddEvent(shortUrl));

        return urlRepository.findUrlByShortUrl(shortUrl).orElseThrow(NoUrlException::new).getOriginalUrl();
    }
}
