package com.young.shortenerurl.url.infrastructures;

import com.young.shortenerurl.url.model.Url;

import java.util.Optional;

public interface UrlRepository {
    Url save(Url url);

    Url getByEncodedUrl(String encodedUrl);

    Boolean existsByOriginUrl(String originUrl);

    Url getByOriginUrl(String originUrl);

    void increaseVisitCount(Long urlId);
}
