package org.prgrms.url.shortner.springbooturlshortner.domain.api;

import javax.validation.Valid;

import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateRequest;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.SearchResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

	@PostMapping(value = "/api/v1/urls")
	public String create(@Valid @ModelAttribute CreateRequest createRequest, Model model, BindingResult bindingResult) {
		CreateResponse createResponse = urlService.create(createRequest);
		model.addAttribute("createResponse", createResponse);
		return "urls";
	}

	@GetMapping(value = "/geonwoo/{shortenUrl}")
	public String findByShortenUrl(@PathVariable(name = "shortenUrl") String shortenUrl) {
		SearchResponse searchResponse = urlService.findByShortenUrl(shortenUrl);
		return "redirect:" + searchResponse.getOriginUrl();
	}

}
