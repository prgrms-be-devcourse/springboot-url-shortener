package com.pgms.shorturlapi.url;

import com.pgms.shorturlcoredomain.url.Url;
import com.pgms.shorturlcoredomain.url.UrlRepository;
import com.pgms.shorturlcoredomain.util.AlgorithmConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pgms.shorturlcoredomain.util.Base62Converter;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final Map<String, AlgorithmConverter> algorithmConverterMap;

    public String getShortUrl(String url, String algorithm){
        AlgorithmConverter algorithmConverter = algorithmConverterMap.get(algorithm);

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

    @Cacheable(value = "originalUrl", key = "#shortUrl", cacheManager = "contentCacheManager")
    public String getOriginalUrl(String shortUrl){
        return urlRepository.findUrlByShortUrl(shortUrl).orElseThrow(RuntimeException::new).getOriginalUrl();
    }
}
