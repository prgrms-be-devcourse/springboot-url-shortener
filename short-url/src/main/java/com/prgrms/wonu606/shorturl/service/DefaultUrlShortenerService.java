package com.prgrms.wonu606.shorturl.service;

import com.prgrms.wonu606.shorturl.domain.HashedShortUrl;
import com.prgrms.wonu606.shorturl.domain.ShortenUrlRepository;
import com.prgrms.wonu606.shorturl.domain.Url;
import com.prgrms.wonu606.shorturl.domain.UrlLink;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateParam;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateResult;
import com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.UniqueShortUrlHashGenerator;
import org.springframework.stereotype.Service;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService {

    private final ShortenUrlRepository shortenUrlRepository;
    private final UniqueShortUrlHashGenerator uniqueShortUrlHashGenerator;

    public DefaultUrlShortenerService(ShortenUrlRepository shortenUrlRepository,
            UniqueShortUrlHashGenerator uniqueShortUrlHashGenerator) {
        this.shortenUrlRepository = shortenUrlRepository;
        this.uniqueShortUrlHashGenerator = uniqueShortUrlHashGenerator;
    }

    @Override
    public ShortenUrlCreateResult createShortenUrlHash(ShortenUrlCreateParam param) {
        Url originalUrl = new Url(param.originalUrl());
        HashedShortUrl uniqueHashedShortUrlHash = uniqueShortUrlHashGenerator.generateUniqueShortUrlHash(originalUrl);

        UrlLink createdUrlLink = new UrlLink(originalUrl, uniqueHashedShortUrlHash);
        shortenUrlRepository.save(createdUrlLink);

        return new ShortenUrlCreateResult(createdUrlLink.getHashedShortUrl().value());
    }
}
