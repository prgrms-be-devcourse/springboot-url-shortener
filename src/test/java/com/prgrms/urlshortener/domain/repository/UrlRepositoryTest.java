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
	@DisplayName("데이터베이스의 sequence 초기값 10000 확인")
	void check_sequence_initial_of_10000() {
	    // given
		UrlInfo urlInfo = new UrlInfo("www.google.com");

	    // when
		UrlInfo savedUrlInfo = repository.save(urlInfo);

		// then
		assertThat(savedUrlInfo.getId()).isEqualTo(10000);
	}
}
