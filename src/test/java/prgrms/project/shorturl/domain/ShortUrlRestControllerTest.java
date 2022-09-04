package prgrms.project.shorturl.domain;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import prgrms.project.shorturl.domain.ShortUrlDto.CreateDto;
import prgrms.project.shorturl.domain.ShortUrlDto.ResponseDto;
import prgrms.project.shorturl.domain.ShortUrlRestController;
import prgrms.project.shorturl.domain.ShortUrlService;

@WebMvcTest(ShortUrlRestController.class)
class ShortUrlRestControllerTest {

	@MockBean
	private ShortUrlService shortUrlService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private final String originUrl = "https://google.com";
	private final String shortenUrl = "abc123";
	private final String shortUrlApiPrefix = "/api/v1/short-urls";

	@Test
	@DisplayName("원본 url 정보를 입력받고 ShortUrl 을 생성한다.")
	void testCreateShortUrl() throws Exception {
		//given
		var createDto = new CreateDto(originUrl, "nanoId");
		var responseDto = new ResponseDto(1L, createDto.originUrl(), shortenUrl, 0);
		var jsonOfCreateDto = objectMapper.writeValueAsString(createDto);
		given(shortUrlService.createShortUrl(createDto)).willReturn(responseDto);

		//when
		var resultActions = mockMvc.perform(
			post(shortUrlApiPrefix).content(jsonOfCreateDto).contentType(APPLICATION_JSON));

		//then
		resultActions.andExpectAll(
			status().isOk(),
			content().contentType(APPLICATION_JSON),
			content().json(objectMapper.writeValueAsString(responseDto)));
	}

	@Test
	@DisplayName("아이디로 ShortUrl 엔티티를 조회한다.")
	void testGetShortUrl() throws Exception {
		//given
		var responseDto = createResponseDto();
		given(shortUrlService.getShortUrl(anyLong())).willReturn(responseDto);

		//when
		var resultActions = mockMvc.perform(
			get(shortUrlApiPrefix + "/{shortUrlId}", 1L).contentType(APPLICATION_JSON));

		//then
		resultActions.andExpectAll(
			status().isOk(),
			content().contentType(APPLICATION_JSON),
			content().json(objectMapper.writeValueAsString(responseDto)));
	}

	@Test
	@DisplayName("리다이렉트 요청 시 numberOfRequests 숫자를 증가시킨다.")
	void testRedirectToOriginUrl() throws Exception {
		//given
		var responseDto = createResponseDto();
		given(shortUrlService.redirectToOriginUrl(anyLong())).willReturn(responseDto);

		//when
		var resultActions = mockMvc.perform(
			put(shortUrlApiPrefix + "/{shortUrlId}", 1L).contentType(APPLICATION_JSON));

		//then
		resultActions.andExpectAll(
			status().isOk(),
			content().contentType(APPLICATION_JSON),
			content().json(objectMapper.writeValueAsString(responseDto)));
	}

	private ResponseDto createResponseDto() {
		return new ResponseDto(1L, originUrl, shortenUrl, 0);
	}
}
