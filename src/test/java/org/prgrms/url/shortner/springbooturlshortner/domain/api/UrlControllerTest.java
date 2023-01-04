package org.prgrms.url.shortner.springbooturlshortner.domain.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateRequest;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.SearchResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class UrlControllerTest {

	@MockBean
	private UrlService urlService;

	@Autowired
	MockMvc mockMvc;

	@Test
	void createTest() throws Exception {
		//given
		CreateResponse createResponse = new CreateResponse("12345678");
		String content = "originUrl=https://geonwoo.com&shortUrlAlgorithm=randomAlgorithm";

		when(urlService.create(any(CreateRequest.class))).thenReturn(createResponse);

		//when & then
		mockMvc.perform(post("/api/v1/urls")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(content))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("createResponse"))
			.andDo(print());

		verify(urlService).create(any(CreateRequest.class));
	}

	@Test
	void findByShortenUrlTest() throws Exception {
		//given
		String originUrl = "geonwoo.com";
		String shortenUrl = "12345678";
		SearchResponse searchResponse = new SearchResponse(originUrl);

		when(urlService.findByShortenUrl(shortenUrl)).thenReturn(searchResponse);

		//when & then
		mockMvc.perform(get("/geonwoo/{shortenUrl}", shortenUrl))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl(searchResponse.getOriginUrl()))
			.andDo(print());

		verify(urlService).findByShortenUrl(shortenUrl);
	}

}