package com.dev.shortenerurl.url.domain.model;

import static org.springframework.util.Assert.*;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "url", indexes = @Index(name = "idx_encoded_url", columnList = "encoded_url"))
public class Url {

	@Id
	private Long id;

	private String originUrl;

	@Embedded
	private EncodedUrl encodedUrl;

	private long requestCount;

	public Url(String originUrl, Long encodingId, String algorithm) {
		notNull(originUrl, "originUrl 은 Null 일 수 없습니다");
		notNull(encodingId, "encodingId 은 Null 일 수 없습니다");
		notNull(algorithm, "algorithm 은 Null 일 수 없습니다");

		this.id = encodingId;
		this.originUrl = originUrl;
		this.encodedUrl = new EncodedUrl(encodingId, algorithm);
		this.requestCount = 0;
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

	public long getRequestCount() {
		return requestCount;
	}

	public void plusRequestCount() {
		this.requestCount++;
	}
}
