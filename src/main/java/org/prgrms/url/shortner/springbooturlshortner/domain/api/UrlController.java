package org.prgrms.url.shortner.springbooturlshortner.domain.api;

import javax.validation.Valid;

import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateRequest;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.SearchResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.service.UrlService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UrlController {

	private final UrlService urlService;

	/**
	 * <pre>
	 *     단축 url을 만듭니다.
	 * </pre>
	 * @param createRequest - 단축할 originUrl과 단축 알고리즘 데이터를 가진 dto
	 * @param model - createResponse를 담을 model
	 * @return - 클라이언트에게 보여줄 view
	 */
	@PostMapping(value = "/api/v1/urls", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String create(@Valid @ModelAttribute CreateRequest createRequest, Model model) {
		CreateResponse createResponse = urlService.create(createRequest);
		model.addAttribute("createResponse", createResponse);
		return "urls";
	}

	/**
	 * <pre>
	 *     shortenUrl로 originUrl을 조회하여 리다이렉트 합니다.
	 * </pre>
	 * @param shortenUrl - 단축 url
	 * @return - 리다이렉트될 originUrl
	 */
	@GetMapping(value = "/geonwoo/{shortenUrl}")
	public String findByShortenUrl(@PathVariable(name = "shortenUrl") String shortenUrl) {
		SearchResponse searchResponse = urlService.findByShortenUrl(shortenUrl);
		return "redirect:" + searchResponse.getOriginUrl();
	}

}
