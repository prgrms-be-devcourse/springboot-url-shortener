package com.programmers.springbooturlshortener.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlController {

	@GetMapping("/")
	public String homePage() {
		return "index";
	}
}
