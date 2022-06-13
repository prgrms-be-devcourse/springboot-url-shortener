package com.prgms.shorturl.url.service;

import com.prgms.shorturl.url.domain.Url;
import com.prgms.shorturl.url.exception.NotFoundUrlException;
import com.prgms.shorturl.url.repository.UrlRepository;
import com.prgms.shorturl.util.Base62Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private static final String LOCALHOST = "http://localhost:8080/";
    private final Base62Util base62Util;
    private final UrlRepository urlRepository;

    @Transactional
    public String shortenUrl(String longUrl) {
        Url url = urlRepository.findByLongUrl(longUrl)
            .orElseGet(() -> urlRepository.save(new Url(longUrl)));

        String shortUrl = base62Util.encoding(url.getId());
        return LOCALHOST+shortUrl;
    }

    @Transactional
    public String getLongUrlByShortUrl (String shortUrl) {
        long id = base62Util.decoding(shortUrl);
        Url findUrl = urlRepository.findById(id)
            .orElseThrow(() -> new NotFoundUrlException("Wrong Url"));
        findUrl.addCallCount();
        return findUrl.getLongUrl();
    }

    public long getCallCount (String shortUrl) {
        long id = base62Util.decoding(shortUrl);
        Url findUrl = urlRepository.findById(id)
            .orElseThrow(() -> new NotFoundUrlException("Wrong Url"));
        return findUrl.getCallCount();
    }
}
