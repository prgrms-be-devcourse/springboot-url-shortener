package com.programmers.springbooturlshortener.url.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlViewController {

	@GetMapping
	public String home() {
		return "home";
	}
}
