package com.prgrms.short_url.service;

import com.prgrms.short_url.domain.Url;
import com.prgrms.short_url.dto.UrlDto;
import com.prgrms.short_url.exception.NoConnectUrlException;
import com.prgrms.short_url.exception.NotFoundException;
import com.prgrms.short_url.repository.UrlRepository;
import com.prgrms.short_url.util.Base58;
import com.prgrms.short_url.util.UrlFormat;
import com.prgrms.short_url.validation.UrlValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlFormat urlFormat;
    private final UrlValidation urlValidation;
    private final Base58 base58;

    public UrlService(UrlRepository urlRepository, UrlFormat urlFormat, UrlValidation urlValidation, Base58 base58) {
        this.urlRepository = urlRepository;
        this.urlFormat = urlFormat;
        this.urlValidation = urlValidation;
        this.base58 = base58;
    }

    @Transactional
    public String createShortUrl(UrlDto urlDto) {

        String originUrl = urlDto.getOriginUrl();

        originUrl = urlValidation.httpValid(originUrl);

        urlDto.setOriginUrl(originUrl);

        if(!urlValidation.connectValid(originUrl)) throw new NoConnectUrlException();

        String shortUrl = urlRepository.getUrlByOriginalUrl(originUrl)
                .map(Url::getShortUrl)
                .orElseGet(() -> save(urlDto));

        return shortUrl;
    }

    public String getShortUrlByOriginalUrl(String originalUrl) {
        Url url = urlRepository.getUrlByOriginalUrl(originalUrl)
                .orElseThrow(NotFoundException::new);
        return url.getShortUrl();
    }

    public String redirect(String shortUrl) {
        shortUrl = urlFormat.getShortUrl(shortUrl);
        Url url = urlRepository.getUrlByShortUrl(shortUrl)
                .orElseThrow(NotFoundException::new);
        return url.getOriginalUrl();
    }

    @Transactional
    public String save(UrlDto urlDto) {
        String base58Str = base58.createRandomShortUrl();
        String shortUrl = urlFormat.getShortUrl(base58Str);

        urlRepository.getUrlByShortUrl(shortUrl)
                .orElseGet(() -> urlRepository.save(Url.builder()
                        .originalUrl(urlDto.getOriginUrl())
                        .shortUrl(shortUrl)
                        .build()));

        return shortUrl;

    }
}
