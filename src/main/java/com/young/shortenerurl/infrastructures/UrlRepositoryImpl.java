package com.young.shortenerurl.infrastructures;

import com.young.shortenerurl.model.Url;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UrlRepositoryImpl implements UrlRepository{

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

}
