package com.programmers.springbooturlshortener.url.presentation;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbooturlshortener.global.domain.exception.EntityNotFound;
import com.programmers.springbooturlshortener.url.application.UrlService;
import com.programmers.springbooturlshortener.url.application.port.UrlRepository;
import com.programmers.springbooturlshortener.url.domain.Url;
import com.programmers.springbooturlshortener.url.presentation.request.UrlCreate;

@AutoConfigureMockMvc
@SpringBootTest
class UrlRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UrlRepository urlRepository;

	@Autowired
	private UrlService urlService;

	@BeforeEach
	void clean() {
		urlRepository.deleteAll();
	}

	@DisplayName("POST: /shorten-url - 요청 Url 정상적이지 않으면 400")
	@Test
	void shortenUrl_invalid_request() throws Exception {
		// given
		UrlCreate request = new UrlCreate("http:/www.naver.com");
		String json = objectMapper.writeValueAsString(request);

		// expected
		mockMvc.perform(post("/shorten")
				.contentType(APPLICATION_JSON)
				.content(json))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(400))
			.andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
			.andExpect(jsonPath("$.validation.originUrl").value("올바르지 않은 URL 형식입니다. 다시 입력해주세요."))
			.andDo(print());
	}

	@DisplayName("POST: /shorten-url - Url 생성 시 DB에 저장 성공")
	@Test
	void shortenUrl_create_success() throws Exception {
		// given
		UrlCreate request = new UrlCreate("https://www.naver.com");
		String json = objectMapper.writeValueAsString(request);

		// when
		mockMvc.perform(post("/shorten")
				.contentType(APPLICATION_JSON)
				.content(json))
			.andExpect(status().isCreated())
			.andDo(print());

		// then
		Url url = urlRepository.findByOriginUrl("naver.com")
			.orElseThrow(EntityNotFound::new);

		assertThat(url.getOriginUrl()).isEqualTo("naver.com");
		assertThat(url.getRequestCount()).isEqualTo(0L);
	}

	@DisplayName("POST: /shorten-url - 이미 존재하는 Url 일 때, 조회 수 증가 성공")
	@Test
	void shortenUrl_increase_request_count_success() throws Exception {
		// given
		UrlCreate request = new UrlCreate("https://www.naver.com");
		urlService.create(request);
		String json = objectMapper.writeValueAsString(request);

		// expected
		mockMvc.perform(post("/shorten")
				.contentType(APPLICATION_JSON)
				.content(json))
			.andExpect(status().isCreated())
			.andDo(print());

		Url result = urlRepository.findById(1L)
			.orElseThrow(EntityNotFound::new);

		assertThat(result.getRequestCount()).isEqualTo(1L);
	}

	@DisplayName("GET: /shorten/{shortUrl} - shortUrl 입력 시 originUrl 조회 성공")
	@Test
	void origin_url_success() throws Exception {
		// given
		UrlCreate urlCreate = new UrlCreate("https://www.naver.com");
		urlService.create(urlCreate);

		Url createdUrl = urlRepository.findByOriginUrl("naver.com")
			.orElseThrow(EntityNotFound::new);

		// expected
		mockMvc.perform(get("/shorten?shortUrl={shortUrl}", createdUrl.getShortUrlKey())
				.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());

		String result = urlService.getOriginUrl(createdUrl.getShortUrlKey());

		assertThat(result).isEqualTo("https://www.naver.com");
	}

	@DisplayName("GET: /{shortUrl} - shortUrl 입력 시 리다이렉트 성공")
	@Test
	void redicrect_success() throws Exception {
		// given
		UrlCreate urlCreate = new UrlCreate("https://www.naver.com");
		urlService.create(urlCreate);

		Url createdUrl = urlRepository.findByOriginUrl("naver.com")
			.orElseThrow(EntityNotFound::new);

		// expected
		mockMvc.perform(get("/{shortUrl}", createdUrl.getShortUrlKey())
				.contentType(APPLICATION_JSON))
			.andExpect(status().isFound())
			.andDo(print());
	}
}
