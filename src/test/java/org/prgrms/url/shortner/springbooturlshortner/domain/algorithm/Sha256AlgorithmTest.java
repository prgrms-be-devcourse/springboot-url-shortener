package org.prgrms.url.shortner.springbooturlshortner.domain.algorithm;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Sha256AlgorithmTest {

	private Sha256Algorithm sha256Algorithm = new Sha256Algorithm();

	@Test
	void executeTest() {

		String originUrl = "geonwoo.com";
		int length = 8;
		String shortenUrl = sha256Algorithm.execute(originUrl);

		log.info("shortenUrl = {}", shortenUrl);
		assertThat(shortenUrl.length()).isEqualTo(length);
	}
}