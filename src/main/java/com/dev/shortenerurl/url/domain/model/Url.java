package com.dev.shortenerurl.url.domain.model;

import org.springframework.util.Assert;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String originUrl;

	private String encodedUrl;

	public Url(String originUrl, String encodedUrl) {
		Assert.notNull(originUrl, "originUrl 은 Null 일 수 없습니다");
		Assert.notNull(encodedUrl, "encodedUrl 은 Null 일 수 없습니다");

		this.originUrl = originUrl;
		this.encodedUrl = encodedUrl;
	}

	public Long getId() {
		return id;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public String getEncodedUrl() {
		return encodedUrl;
	}
}
