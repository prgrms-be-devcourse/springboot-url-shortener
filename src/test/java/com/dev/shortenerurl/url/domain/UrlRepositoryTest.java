package com.dev.shortenerurl.url.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dev.shortenerurl.url.domain.model.Url;
import com.dev.shortenerurl.url.domain.model.query.OriginUrlModel;

@DisplayName("[UrlRepository 테스트]")
@DataJpaTest
class UrlRepositoryTest {

	@Autowired
	private UrlRepository urlRepository;

	@Test
	@DisplayName("[encodedUrl 로 originUrl 을 조회한다]")
	void test() {
		//given
		String originUrl = "originUrl";
		Url url = urlRepository.save(new Url(originUrl, 100L, "BASE_62"));

		//when
		Optional<OriginUrlModel> result = urlRepository.findOriginUrlByEncodedUrl(url.getEncodedUrl());

		//then
		assertThat(result).isPresent();
		OriginUrlModel originUrlModel = result.get();

		assertThat(originUrlModel.originUrl()).isEqualTo(originUrl);
		assertThat(originUrlModel.requestCount()).isZero();
	}
}