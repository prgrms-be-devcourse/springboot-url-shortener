package com.prgrms.wonu606.shorturl.service;

import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.domain.Url;
import com.prgrms.wonu606.shorturl.domain.UrlLink;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateParam;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateResult;
import com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.UniqueUrlHashCreator;
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
    public ShortenUrlCreateResult createShortenUrlHash(ShortenUrlCreateParam param) {
        Url originalUrl = new Url(param.originalUrl());
        UrlHash uniqueUrlHashHash = uniqueUrlHashCreator.create(originalUrl);

        UrlLink createdUrlLink = new UrlLink(originalUrl, uniqueUrlHashHash);
        urlLinkRepository.save(createdUrlLink);

        return new ShortenUrlCreateResult(createdUrlLink.getUrlHash().value());
    }
}
