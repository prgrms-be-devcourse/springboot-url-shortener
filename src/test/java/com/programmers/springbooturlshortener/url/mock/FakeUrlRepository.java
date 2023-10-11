package com.programmers.springbooturlshortener.url.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.programmers.springbooturlshortener.url.application.port.UrlRepository;
import com.programmers.springbooturlshortener.url.domain.Url;

public class FakeUrlRepository implements UrlRepository {

	private final AtomicLong idGenerator = new AtomicLong(0);
	private final List<Url> data = new ArrayList<>();

	@Override
	public Optional<Url> findById(Long id) {
		return data.stream()
			.filter(url -> url.getUrlId().equals(id))
			.findAny();
	}

	@Override
	public Optional<Url> findByOriginUrl(String originUrl) {
		return data.stream()
			.filter(url -> url.getOriginUrl().equals(originUrl))
			.findAny();
	}

	@Override
	public Url save(Url url) {
		if (url.getUrlId() == null) {
			Url createdUrl = Url.builder()
				.urlId(idGenerator.incrementAndGet())
				.originUrl(url.getOriginUrl())
				.build();
			data.add(createdUrl);
			return createdUrl;
		} else {
			data.removeIf(existingUrl -> existingUrl.getUrlId().equals(url.getUrlId()));
			data.add(url);
			return url;
		}
	}

	@Override
	public void deleteAll() {
		data.clear();
	}
}
