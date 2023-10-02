package com.young.shortenerurl.infrastructures;

import com.young.shortenerurl.model.Url;

import java.util.Optional;

public interface UrlRepository {
    Url save(Url url);

    Optional<Url> findByEncodedUrl(String encodedUrl);
}
