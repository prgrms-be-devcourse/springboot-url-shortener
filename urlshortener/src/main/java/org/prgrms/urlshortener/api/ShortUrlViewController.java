package org.prgrms.urlshortener.api;

import org.prgrms.urlshortener.application.ShortUrlService;
import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.dto.response.OriginUrlResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShortUrlViewController {

	private final ShortUrlService shortUrlService;

	@GetMapping("/{baseUrl}/{encodedUrl}")
	public String redirectToOriginUrl(
		@PathVariable String baseUrl,
		@PathVariable String encodedUrl,
		@RequestParam Algorithm algorithm
	) {
		OriginUrlResponse response = shortUrlService.getOriginUrl(baseUrl, encodedUrl, algorithm);
		String originUrl = response.originUrl();

		return "redirect:https://" + originUrl;
	}

}
