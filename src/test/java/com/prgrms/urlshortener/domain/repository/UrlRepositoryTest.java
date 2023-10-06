package com.prgrms.urlshortener.domain.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prgrms.urlshortener.domain.entity.UrlInfo;

@SpringBootTest
class UrlRepositoryTest {

	@Autowired
	UrlRepository repository;

	@Test
	@DisplayName("Database sequence 초기값 123456 확인")
	void sequence_initial_of_123456_test() {
	    // given
		UrlInfo urlInfo = new UrlInfo("www.google.com");

	    // when
		UrlInfo savedUrlInfo = repository.save(urlInfo);

		// then
		assertThat(savedUrlInfo.getId()).isEqualTo(123456);
	}
}
