package com.programmers.springbooturlshortener.domain.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.programmers.springbooturlshortener.domain.url.dto.UrlResponseDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;

@Component
public class UrlServiceFacade {

	private final UrlService urlService;

	@Autowired
	public UrlServiceFacade(UrlService urlService) {
		this.urlService = urlService;
	}

	public UrlResponseDto createShortUrl(UrlServiceRequestDto urlRequestDto) {

		UrlResponseDto urlResponseDto;
		while (true) {
			try {
				urlResponseDto = urlService.createShortUrl(urlRequestDto);
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(30);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
		return urlResponseDto;
	}
}
