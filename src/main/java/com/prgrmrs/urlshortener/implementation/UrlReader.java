package com.prgrmrs.urlshortener.implementation;

import static com.prgrmrs.urlshortener.exception.ErrorCode.URL_NOT_FOUND;

import com.prgrmrs.urlshortener.exception.UrlShortenerException;
import com.prgrmrs.urlshortener.model.UrlMapping;
import com.prgrmrs.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Component;

@Component
public class UrlReader {

    private final UrlRepository repository;

    public UrlReader(UrlRepository repository) {
        this.repository = repository;
    }

    public UrlMapping retrieveUrlMapping(String hash) {
        return repository.findByHash(hash)
                .orElseThrow(() -> new UrlShortenerException(URL_NOT_FOUND));
    }
}
