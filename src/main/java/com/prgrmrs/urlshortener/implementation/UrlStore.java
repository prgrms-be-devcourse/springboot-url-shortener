package com.prgrmrs.urlshortener.implementation;

import com.prgrmrs.urlshortener.model.UrlMapping;
import com.prgrmrs.urlshortener.model.vo.OriginalUrl;
import com.prgrmrs.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Component;

@Component
public class UrlStore {

    private final UrlRepository repository;

    public UrlStore(UrlRepository repository) {
        this.repository = repository;
    }

    public UrlMapping initializeUrlMapping(OriginalUrl originalUrl) {
        return repository.save(new UrlMapping(originalUrl));
    }

    public UrlMapping finalizeUrlMapping(UrlMapping savingUrlMapping, String createdHash) {
        savingUrlMapping.embedHash(createdHash);

        return savingUrlMapping;
    }
}
