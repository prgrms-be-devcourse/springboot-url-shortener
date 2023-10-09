package com.prgrms.wonu606.shorturl.service;

import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.domain.Url;
import com.prgrms.wonu606.shorturl.domain.UrlLink;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateParam;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateResult;
import com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.UniqueUrlHashCreator;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService {

    private final UrlLinkRepository urlLinkRepository;
    private final UniqueUrlHashCreator uniqueUrlHashCreator;

    public DefaultUrlShortenerService(UrlLinkRepository urlLinkRepository,
            UniqueUrlHashCreator uniqueUrlHashCreator) {
        this.urlLinkRepository = urlLinkRepository;
        this.uniqueUrlHashCreator = uniqueUrlHashCreator;
    }

    @Override
    public ShortenUrlCreateResult findOrCreateShortenUrlHash(ShortenUrlCreateParam param) {
        Url originalUrl = new Url(param.originalUrl());

        Optional<UrlLink> urlLinkOptional = urlLinkRepository.findByOriginal(originalUrl);
        if (urlLinkOptional.isPresent()) {
            UrlLink foundUrlLink = urlLinkOptional.get();
            return new ShortenUrlCreateResult(foundUrlLink.getUrlHash().value(), false);
        }

        return createUrlHash(originalUrl);
    }

    @Override
    public String getOriginalUrlByShortUrl(String shortUrl) {
        UrlHash findUrlHash = new UrlHash(shortUrl);

        return urlLinkRepository.findByUrlHash(findUrlHash)
                .map(UrlLink::getOriginalUrl)
                .map(Url::value)
                .orElseThrow(() -> new UrlNotFoundException("존재 하지 않는 Short URL입니다. Current Short Url: " + shortUrl));
    }

    private ShortenUrlCreateResult createUrlHash(Url originalUrl) {
        UrlHash uniqueUrlHashHash = uniqueUrlHashCreator.create(originalUrl);

        UrlLink createdUrlLink = new UrlLink(originalUrl, uniqueUrlHashHash);
        urlLinkRepository.save(createdUrlLink);

        return new ShortenUrlCreateResult(createdUrlLink.getUrlHash().value(), true);
    }
}
