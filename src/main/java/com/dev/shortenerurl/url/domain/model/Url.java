package com.dev.shortenerurl.url.domain.model;

import org.springframework.util.Assert;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Url {

	@Id
	private Long id;

	private String originUrl;

	@Embedded
	private EncodedUrl encodedUrl;

	public Url(String originUrl, Long encodingId, String algorithm) {
		Assert.notNull(originUrl, "originUrl 은 Null 일 수 없습니다");
		Assert.notNull(encodingId, "encodingId 은 Null 일 수 없습니다");
		Assert.notNull(algorithm, "algorithm 은 Null 일 수 없습니다");

		this.id = encodingId;
		this.originUrl = originUrl;
		this.encodedUrl = new EncodedUrl(encodingId, algorithm);
	}

	public Long getId() {
		return id;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public String getEncodedUrl() {
		return encodedUrl.getEncodedUrl();
	}
}
