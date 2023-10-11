package com.programmers.springbooturlshortener.url.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.programmers.springbooturlshortener.url.application.port.UrlRepository;
import com.programmers.springbooturlshortener.url.domain.Url;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UrlRepositoryImpl implements UrlRepository {

	private final UrlJpaRepository urlJpaRepository;

	@Override
	public Optional<Url> findById(Long id) {
		return urlJpaRepository.findById(id).map(UrlEntity::toModel);
	}

	@Override
	public Optional<Url> findByOriginUrl(String url) {
		return urlJpaRepository.findUrlEntityByOriginUrl(url).map(UrlEntity::toModel);
	}

	@Override
	public Url save(Url url) {
		return urlJpaRepository.save(UrlEntity.from(url)).toModel();
	}

	@Override
	public void deleteAll() {
		urlJpaRepository.deleteAll();
	}
}
