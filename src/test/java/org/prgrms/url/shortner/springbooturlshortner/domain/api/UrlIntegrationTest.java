package org.prgrms.url.shortner.springbooturlshortner.domain.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.prgrms.url.shortner.springbooturlshortner.domain.Url;
import org.prgrms.url.shortner.springbooturlshortner.domain.algorithm.Sha256Algorithm;
import org.prgrms.url.shortner.springbooturlshortner.domain.repository.UrlJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlIntegrationTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private Sha256Algorithm sha256Algorithm;

	@Autowired
	private UrlJpaRepository urlJpaRepository;

	@Test
	void createTest() throws Exception {

		String content = "originUrl=https://geonwoo.com&shortUrlAlgorithm=randomAlgorithm";

		mockMvc.perform(post("/api/v1/urls")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(content))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("createResponse"))
			.andExpect(view().name("urls"))
			.andDo(print());
	}

	@Test
	void findByShortenUrlTest() throws Exception {

		Url url = Url.builder()
			.originUrl("naver.com")
			.shortUrlAlgorithm(sha256Algorithm)
			.build();

		url.allocateShortenUrl("12345678");

		urlJpaRepository.save(url);

		mockMvc.perform(get("/geonwoo/{shortenUrl}", url.getShortenUrl()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl(url.getOriginUrl()))
			.andDo(print());
	}

}
