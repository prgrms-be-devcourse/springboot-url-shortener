package com.dev.shortenerurl.url.domain;

import java.util.Optional;

import com.dev.shortenerurl.url.domain.model.Url;
import com.dev.shortenerurl.url.domain.model.query.OriginUrlModel;

public interface UrlRepository {

	Url save(Url url);

	Optional<Url> findByOriginUrl(String originUrl);

	Optional<OriginUrlModel> findByEncodedUrl(String encodedUrl);
}
