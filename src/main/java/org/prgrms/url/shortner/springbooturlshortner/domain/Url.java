package org.prgrms.url.shortner.springbooturlshortner.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.prgrms.url.shortner.springbooturlshortner.domain.algorithm.ShortUrlAlgorithm;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "shorten_url", nullable = false, unique = true)
	private String shortenUrl;

	@Column(name = "origin_url", nullable = false)
	private String originUrl;

	@Transient
	private ShortUrlAlgorithm shortUrlAlgorithm;

	@Builder
	public Url(String originUrl, ShortUrlAlgorithm shortUrlAlgorithm) {
		validateOriginUrl(originUrl);
		validateShortUrlAlgorithm(shortUrlAlgorithm);
		this.originUrl = originUrl;
		this.shortUrlAlgorithm = shortUrlAlgorithm;
	}

	private void validateOriginUrl(String originUrl) {
		if (!StringUtils.hasText(originUrl)) {
			throw new IllegalArgumentException("올바른 Url이 아닙니다.");
		}
	}

	private void validateShortUrlAlgorithm(ShortUrlAlgorithm shortUrlAlgorithm) {
		if (!Objects.nonNull(shortUrlAlgorithm)) {
			throw new IllegalArgumentException("단축 알고리즘은 Null일 수 없습니다.");
		}
	}

	public void allocateShortenUrl(String shortenUrl) {
		this.shortenUrl = shortenUrl;
	}

	public String createShortenUrl() {
		return shortUrlAlgorithm.execute(this.originUrl);
	}
}
