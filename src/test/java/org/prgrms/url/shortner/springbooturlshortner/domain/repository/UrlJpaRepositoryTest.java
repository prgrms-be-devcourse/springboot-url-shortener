package org.prgrms.url.shortner.springbooturlshortner.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.prgrms.url.shortner.springbooturlshortner.domain.Url;
import org.prgrms.url.shortner.springbooturlshortner.domain.algorithm.RandomAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UrlJpaRepositoryTest {

	@Mock
	private RandomAlgorithm randomAlgorithm;

	@Autowired
	private UrlJpaRepository urlJpaRepository;

	@Test
	void findUrlByShortenUrlEqualsTest() {
		String originUrl = "geonwoo.com";
		Url url = Url.builder()
			.originUrl(originUrl)
			.shortUrlAlgorithm(randomAlgorithm)
			.build();

		url.allocateShortenUrl("gAoGVgGL");
		urlJpaRepository.save(url);

		Optional<Url> optionalUrl = urlJpaRepository.findUrlByShortenUrlEquals(url.getShortenUrl());

		assertThat(optionalUrl).isPresent();
		Url findUrl = optionalUrl.get();
		assertThat(findUrl)
			.hasFieldOrPropertyWithValue("originUrl", url.getOriginUrl())
			.hasFieldOrPropertyWithValue("shortenUrl", url.getShortenUrl());
	}

}