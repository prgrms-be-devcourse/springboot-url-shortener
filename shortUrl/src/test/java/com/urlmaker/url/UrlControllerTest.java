package com.urlmaker.url;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.urlmaker.dto.UrlCreateRequestDTO;
import com.urlmaker.dto.UrlCreateResponseDTO;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UrlService urlService;

	@Test
	@DisplayName("url 주소를 입력하고 shortenUrl로 리다이렉트 된다.")
	void enrollUrl() throws Exception{
		UrlCreateRequestDTO request = new UrlCreateRequestDTO("www.naver.com", "Base62");
		UrlCreateResponseDTO response = new UrlCreateResponseDTO("response.com", 0);

		when(urlService.createShortenUrl(Mockito.any(UrlCreateRequestDTO.class)))
				.thenReturn(response);

		mvc.perform(MockMvcRequestBuilders.post("/shortenUrl")
			.flashAttr("urlCreateRequestDTO",request))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/shortenUrl"));
	}

}