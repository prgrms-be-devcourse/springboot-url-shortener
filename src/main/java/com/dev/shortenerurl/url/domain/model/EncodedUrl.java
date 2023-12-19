package com.dev.shortenerurl.url.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class EncodedUrl {

	@Column(name = "encoded_url", nullable = false, unique = true)
	private String value;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EncodingAlgorithms algorithm;

	public EncodedUrl(Long id, String algorithm) {
		this.algorithm = EncodingAlgorithms.valueOf(algorithm);
		value = this.algorithm.encode(id);
	}

	public String getEncodedUrl() {
		return value;
	}
}
