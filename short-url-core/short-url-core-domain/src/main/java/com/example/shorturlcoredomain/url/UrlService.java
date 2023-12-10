package com.example.shorturlcoredomain.url;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.Base62Converter;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    public String getShortUrl(String url){
        Url findUrl = urlRepository.findUrlByOriginal(url).orElseGet(() -> {
                    Url createdUrl = urlRepository.save(Url.builder().original(url).build());
                    String shortUrl = Base62Converter.encode(createdUrl.getId());
                    createdUrl.updateUrlShortUrl(shortUrl);
                    return createdUrl;
                }
        );

        return findUrl.getShortUrl();
    }
}
