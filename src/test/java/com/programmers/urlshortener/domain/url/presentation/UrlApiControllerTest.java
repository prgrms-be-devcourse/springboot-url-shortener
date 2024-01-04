package com.programmers.urlshortener.domain.url.presentation;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.urlshortener.domain.url.application.UrlService;
import com.programmers.urlshortener.domain.url.application.dto.request.ShortUrlCreateRequest;
import com.programmers.urlshortener.domain.url.application.dto.response.ShortUrlResponse;
import com.programmers.urlshortener.global.error.ErrorCode;
import com.programmers.urlshortener.global.error.exception.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UrlApiController.class)
class UrlApiControllerTest {

	@MockBean
	private UrlService urlService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("Short Url 생성 성공 테스트")
	@Test
	void createShortUrl() throws Exception {
		//given
		ShortUrlCreateRequest request = new ShortUrlCreateRequest("https://github.com/hyee0715", "BASE62");
		ShortUrlResponse response = new ShortUrlResponse(1L, "https://github.com/hyee0715", "abcd", 0, "BASE62", LocalDateTime.now());

		when(urlService.createShortUrl(request)).thenReturn(response);

		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/short-urls")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.originalUrl").value("https://github.com/hyee0715"))
			.andExpect(jsonPath("$.shortUrl").value("abcd"))
			.andExpect(jsonPath("$.requestCount").value(0))
			.andExpect(jsonPath("$.encoderType").value("BASE62"))
			.andExpect(jsonPath("$.createdAt").isNotEmpty());

	}

	@DisplayName("Short Url 생성 실패 테스트")
	@Test
	void createShortUrlFail() throws Exception {
		//given
		ShortUrlCreateRequest request = new ShortUrlCreateRequest("", "BASE62");
		ShortUrlResponse response = new ShortUrlResponse(1L, "https://github.com/hyee0715", "abcd", 0, "BASE62", LocalDateTime.now());

		when(urlService.createShortUrl(request)).thenReturn(response);

		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/short-urls")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.timestamp").isNotEmpty())
			.andExpect(jsonPath("$.code").value("BAD_REQUEST"))
			.andExpect(jsonPath("$.errors").isNotEmpty())
			.andExpect(jsonPath("$.message").value("입력하신 URL이 유효하지 않습니다."));
	}

	@DisplayName("Short Url을 이용한 Url 조회 테스트")
	@Test
	void findByShortUrl() throws Exception {
		//given
		String shortUrl = "abc";

		ShortUrlResponse response = new ShortUrlResponse(1L, "https://github.com/hyee0715", "abc", 0, "BASE62", LocalDateTime.now());

		when(urlService.findByShortUrl(shortUrl)).thenReturn(response);

		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.get("/api/" + shortUrl)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.originalUrl").value("https://github.com/hyee0715"))
			.andExpect(jsonPath("$.shortUrl").value("abc"))
			.andExpect(jsonPath("$.requestCount").value(0))
			.andExpect(jsonPath("$.encoderType").value("BASE62"))
			.andExpect(jsonPath("$.createdAt").isNotEmpty());
	}

	@DisplayName("Short Url을 이용한 Url 삭제 성공 테스트")
	@Test
	void deleteByShortUrl() throws Exception {
		//given
		String shortUrl = "abc";
		doNothing().when(urlService).deleteByShortUrl(shortUrl);

		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/" + shortUrl)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@DisplayName("Short Url을 이용한 Url 삭제 실패 테스트")
	@Test
	void deleteByShortUrlFail() throws Exception {
		//given
		String shortUrl = "abc";
		doThrow(new EntityNotFoundException(ErrorCode.URL_NOT_FOUND)).when(urlService).deleteByShortUrl(anyString());

		//when
		//then
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/" + shortUrl)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.timestamp").isNotEmpty())
			.andExpect(jsonPath("$.code").value("NOT_FOUND"))
			.andExpect(jsonPath("$.errors").isArray())
			.andExpect(jsonPath("$.message").value("URL을 찾을 수 없습니다."));
	}
}
