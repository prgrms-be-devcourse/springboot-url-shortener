package com.jhs.shortenerurl.service;

import com.jhs.shortenerurl.domain.Url;
import com.jhs.shortenerurl.repository.UrlRepository;
import com.jhs.shortenerurl.service.convert.Mode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shorten(String url, Mode mode) {
        return urlRepository.findByOriginUrl(url)
                .orElseGet(() -> createNewShortenUrl(url, mode));
    }

    private String createNewShortenUrl(String originUrl, Mode mode) {
        Url url = new Url(originUrl);
        Long id = urlRepository.save(url).getId();

        String shortenUrl = mode.shorten(id);
        url.assignShortenUrl(shortenUrl);

        return shortenUrl;
    }

}
