package com.young.shortenerurl.url.infrastructures;

import com.young.shortenerurl.url.model.Url;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UrlRepositoryImpl implements UrlRepository {

    private final UrlJpaRepository urlJpaRepository;

    public UrlRepositoryImpl(UrlJpaRepository urlJpaRepository) {
        this.urlJpaRepository = urlJpaRepository;
    }

    @Override
    public Url save(Url url) {
        return urlJpaRepository.save(url);
    }

    @Override
    public Optional<Url> findByEncodedUrl(String encodedUrl) {
        return urlJpaRepository.findByEncodedUrl(encodedUrl);
    }

    @Override
    public Optional<Url> findByOriginUrl(String originUrl) {
        return urlJpaRepository.findUrlByOriginUrl(originUrl);
    }

}
