package com.programmers.urlshortener.domain.url.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import com.programmers.urlshortener.domain.BaseEntity;
import com.programmers.urlshortener.domain.encoder.domain.EncoderType;
import com.programmers.urlshortener.global.error.ErrorCode;
import com.programmers.urlshortener.global.error.exception.InvalidValueException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url extends BaseEntity {

	private static final String URL_REGEX = "(https?:\\/\\/)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
	private static final String PREFIX_PROTOCOL = "https://";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String originalUrl;

	@Column(unique = true)
	private String shortUrl;

	private int requestCount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EncoderType encoderType;

	@Builder
	public Url(String originalUrl, EncoderType encoderType) {
		validateUrlFormat(originalUrl);
		this.originalUrl = addProtocolToUrl(originalUrl);
		this.requestCount = 0;
		this.encoderType = encoderType;
	}

	public void addRequestCount() {
		this.requestCount++;
	}

	public void validateUrlFormat(String url) {
		if (url == null || url.isEmpty() || !Pattern.matches(URL_REGEX, url)) {
			throw new InvalidValueException(ErrorCode.INVALID_URL_VALUE);
		}
	}

	public String addProtocolToUrl(String url) {
		if (!url.startsWith(PREFIX_PROTOCOL)) {
			url = PREFIX_PROTOCOL + url;
		}

		return url;
	}

	public void updateShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
}
