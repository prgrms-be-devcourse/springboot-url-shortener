package org.daehwi.shorturl.domain;

import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "short_url", indexes = @Index(name = "idx_short_url", columnList = "shortUrl"))
@NoArgsConstructor(access = PROTECTED)
public class ShortUrl {

	@Id
	private Long id;

	@Column(name = "origin_url", nullable = false)
	private String originUrl;

	@Column(name = "short_url", nullable = false)
	private String shortUrl;

	@Column(name = "request_count", nullable = false)
	private long requestCount;

	public ShortUrl(Long id, String originUrl, String shortUrl) {
		this.id = id;
		this.originUrl = originUrl;
		this.shortUrl = shortUrl;
	}

	public void increaseRequestCount() {
		++this.requestCount;
	}
}
