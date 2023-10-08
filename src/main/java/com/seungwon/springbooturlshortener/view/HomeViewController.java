package com.seungwon.springbooturlshortener.view;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.seungwon.springbooturlshortener.application.UrlService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeViewController {

	private final UrlService urlService;

	@GetMapping
	public String home() {
		return "home";
	}

	@GetMapping("/{key}")
	public ResponseEntity<Object> urlLoad(@PathVariable String key) {
		String originalUrl = urlService.loadUrl(key);

		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.setLocation(URI.create(originalUrl));

		return new ResponseEntity<>(httpHeader, MOVED_PERMANENTLY);
	}
}
