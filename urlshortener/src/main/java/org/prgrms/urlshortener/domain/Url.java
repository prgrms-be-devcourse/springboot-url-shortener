package org.prgrms.urlshortener.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "urls")
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "origin_url", updatable = false, nullable = false)
	private String originUrl;

	@Column(name = "encoded_url")
	private String encodedUrl;

	@Column(name = "hit_count")
	private int hitCount;

	@Enumerated(EnumType.STRING)
	@Column(name = "algorithm")
	private Algorithm algorithm;

	public Url(String originUrl, Algorithm algorithm) {
		this.originUrl = originUrl;
		this.algorithm = algorithm;
	}

	public void enrollEncodedUrl(String encodedUrl) {
		this.encodedUrl = encodedUrl;
	}

	public void plusHitCount() {
		this.hitCount++;
	}
}
