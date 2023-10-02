package com.youngurl.shortenerurl.infrastructures;

import com.youngurl.shortenerurl.model.Url;
import org.springframework.stereotype.Repository;

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

}
