package com.programmers.springbooturlshortener.url.application.port;

import java.util.Optional;

import com.programmers.springbooturlshortener.url.domain.Url;

public interface UrlRepository {

	Optional<Url> findById(Long id);

	Optional<Url> findByOriginUrl(String url);

	Url save(Url url);

	void deleteAll();
}
