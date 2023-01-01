package org.prgrms.url.shortner.springbooturlshortner.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "shorten_url",nullable = false)
	private String shortenUrl;

	@Column(name = "origin_url",nullable = false)
	private String originUrl;

	@Column(name = "view_count",nullable = false)
	private int viewCount;
}
