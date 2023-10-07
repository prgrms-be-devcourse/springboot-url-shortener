package com.seungwon.springbooturlshortener.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.seungwon.springbooturlshortener.application.UrlService;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateRequest;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeViewController {

	private final UrlService urlService;

	@GetMapping
	@PostMapping
	public String home() {
		return "home";
	}
}
