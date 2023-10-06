package com.prgrms.urlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	@PostMapping("/shorten")
	public String shorten() {
		return "shorten-result";
	}
}
