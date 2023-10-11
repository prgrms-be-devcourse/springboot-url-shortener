package com.prgrmrs.urlshortener.business;

import com.prgrmrs.urlshortener.implementation.UrlReader;
import com.prgrmrs.urlshortener.implementation.UrlStore;
import com.prgrmrs.urlshortener.model.UrlMapping;
import com.prgrmrs.urlshortener.model.vo.OriginalUrl;
import com.prgrmrs.urlshortener.util.Base62;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlService {

    private final UrlStore store;
    private final UrlReader reader;

    public UrlService(UrlStore store, UrlReader reader) {
        this.store = store;
        this.reader = reader;
    }

    @Transactional
    public UrlMapping shortenUrl(OriginalUrl originalUrl) {
        UrlMapping initialUrlMapping = store.initializeUrlMapping(originalUrl);
        String createdHash = Base62.encode(initialUrlMapping.getSequence());

        return store.finalizeUrlMapping(initialUrlMapping, createdHash);
    }

    @Transactional
    public OriginalUrl redirectUrl(String hash) {
        UrlMapping urlMapping = reader.retrieveUrlMapping(hash);
        urlMapping.increaseViewCount();

        return urlMapping.getOriginalUrl();
    }
}
