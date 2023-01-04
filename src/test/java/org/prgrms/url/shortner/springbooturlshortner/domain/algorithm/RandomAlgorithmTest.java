package org.prgrms.url.shortner.springbooturlshortner.domain.algorithm;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class RandomAlgorithmTest {

	private RandomAlgorithm randomAlgorithm = new RandomAlgorithm();

	@Test
	void executeTest() {

		String originUrl = "geonwoo.com";
		int length = 8;
		String shortenUrl = randomAlgorithm.execute(originUrl);

		log.info("shortenUrl = {}", shortenUrl);
		assertThat(shortenUrl.length()).isEqualTo(length);
	}
}