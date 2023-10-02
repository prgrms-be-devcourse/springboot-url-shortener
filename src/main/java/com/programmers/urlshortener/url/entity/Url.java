package com.programmers.urlshortener.url.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;

	@Column(name = "shortening_key", length = 7)
	private String shorteningKey;

	@Column(name = "original_url", nullable = false)
	private String originalUrl;

	public Url(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public void addShorteningKey(String shorteningKey) {
		this.shorteningKey = shorteningKey;
	}
}
