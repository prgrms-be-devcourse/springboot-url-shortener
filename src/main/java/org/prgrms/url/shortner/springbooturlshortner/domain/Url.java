package org.prgrms.url.shortner.springbooturlshortner.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private String shortenKey;

	@Column(name = "origin_url",nullable = false)
	private String originUrl;

	@Column(name = "view_count",nullable = false)
	private int viewCount;

	@Builder
	public Url(String shortenKey, String originUrl) {
		this.shortenKey = shortenKey;
		this.originUrl = originUrl;
	}
}
