package com.programmers.springbooturlshortener.url.infrastructure;

import com.programmers.springbooturlshortener.url.domain.Url;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "urls")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "url_id")
	private Long urlId;

	@Column(name = "origin_url", unique = true, nullable = false)
	private String originUrl;

	@Column(name = "short_url_key", unique = true, length = 8)
	private String shortUrlKey;

	@Column(name = "request_count", nullable = false)
	private long requestCount;

	public static UrlEntity from(Url url) {
		UrlEntity urlEntity = new UrlEntity();
		urlEntity.urlId = url.getUrlId();
		urlEntity.originUrl = url.getOriginUrl();
		urlEntity.shortUrlKey = url.getShortUrlKey();
		urlEntity.requestCount = url.getRequestCount();

		return urlEntity;
	}

	public Url toModel() {
		return Url.builder()
			.urlId(urlId)
			.originUrl(originUrl)
			.shortUrlKey(shortUrlKey)
			.requestCount(requestCount)
			.build();
	}
}
