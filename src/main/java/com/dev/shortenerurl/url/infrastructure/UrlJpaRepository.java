package com.dev.shortenerurl.url.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.shortenerurl.url.domain.UrlRepository;
import com.dev.shortenerurl.url.domain.model.Url;
import com.dev.shortenerurl.url.domain.model.query.OriginUrlModel;

public interface UrlJpaRepository extends JpaRepository<Url, Long>, UrlRepository {

	Optional<Url> findByOriginUrl(String originUrl);

	default Optional<OriginUrlModel> findOriginUrlByEncodedUrl(String encodedUrl) {
		return findByEncodedUrlValue(encodedUrl);
	}

	//not implement method
	Optional<OriginUrlModel> findByEncodedUrlValue(String encodedUrl);
}
