package com.blessing333.urlshortner.domain.application;

import com.blessing333.urlshortner.domain.model.url.Url;
import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.UrlRepository;
import com.blessing333.urlshortner.domain.model.url.shortener.ShortUrl;
import com.blessing333.urlshortner.domain.model.url.shortener.UrlShorteningManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlServiceImpl implements UrlService {
    private final UrlShorteningManager urlShorteningManager;
    private final UrlRepository urlRepository;

    @Override
    @Transactional
    public String registerShortenUrl(UrlCreateCommand command) {
        Url url = urlRepository.findByOriginalUrl(command.getOriginalUrl());
        if (url != null) {
            return url.getId();
        }
        Long urlSequence = urlRepository.getNextValFromURLSequence();
        command.setUrlSequence(urlSequence);
        ShortUrl shortUrl = urlShorteningManager.shorteningUrl(command);
        Url newUrl = Url.createNewUrl(shortUrl.getKey(), command.getOriginalUrl(), shortUrl.getNewUrl());
        urlRepository.save(newUrl);
        return newUrl.getId();
    }

    @Transactional
    @Override
    public Url loadUrlForRedirect(String id) {
        Url url = urlRepository.findUrlForUpdateById(id);
        url.increaseViewCount();
        return url;
    }

    @Override
    public Url loadUrlById(String urlId) {
        return urlRepository.findById(urlId);
    }
}
