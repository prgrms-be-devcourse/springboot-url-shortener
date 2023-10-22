package com.seungwon.springbooturlshortener.domain;

import org.apache.commons.validator.routines.UrlValidator;

import com.seungwon.springbooturlshortener.exception.InvalidUrlException;

import jakarta.persistence.Column;
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

	@Column(nullable = false)
	private String originalUrl;

	@Column(unique = true)
	private String shortUrlKey;

	private static final int KEY_MAX_LENGTH = 7;

	public Url(String originalUrl) {
		if (!isValid(originalUrl)) {
			throw new InvalidUrlException();
		}

		this.originalUrl = originalUrl;
	}

	public boolean isValid(String url) {
		UrlValidator validator = new UrlValidator();

		return validator.isValid(url);
	}

	public void saveShortUrlKey(String shortUrlKey) {
		if (shortUrlKey.length() > KEY_MAX_LENGTH) {
			shortUrlKey = shortUrlKey.substring(0, KEY_MAX_LENGTH);
		}

		this.shortUrlKey = shortUrlKey;
	}
}
