package com.youngurl.shortenerurl.infrastructures;

import com.youngurl.shortenerurl.model.Url;

import java.util.Optional;

public interface UrlRepository {
    Url save(Url url);

    Optional<Url> findByEncodedUrl(String encodedUrl);
}
