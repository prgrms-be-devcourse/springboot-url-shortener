package com.seungwon.springbooturlshortener.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeViewController {

	@GetMapping
	@PostMapping
	public String home() {
		return "home";
	}
}
