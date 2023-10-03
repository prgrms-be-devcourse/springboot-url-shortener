package com.seungwon.springbooturlshortener.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String originalUrl;

	private String shortUrlKey;

	public Url(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public void saveShortUrlKey(String shortUrlKey){
		this.shortUrlKey = shortUrlKey;
	}

	//TODO : 필터링할 링크(https만 할지, 차단할 도메인 있을지)
}
