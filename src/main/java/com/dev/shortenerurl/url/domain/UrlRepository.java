package com.dev.shortenerurl.url.domain;

import java.util.Optional;

import com.dev.shortenerurl.url.domain.model.Url;

public interface UrlRepository {

	Url save(Url url);

	Optional<Url> findByOriginUrl(String originUrl);

	Optional<Url> findByEncodedUrl(String encodedUrl);
}
