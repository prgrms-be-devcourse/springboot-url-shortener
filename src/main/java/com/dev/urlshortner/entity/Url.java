package com.dev.urlshortner.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "short_key", unique = true)
	private String shortKey;
	@Column(name = "original_url")
	private String originalUrl;
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
	@Column(name = "visitCount")
	private Long visitCount = 0L;

	public Url(String shortKey, String originalUrl) {
		this.shortKey = shortKey;
		this.originalUrl = originalUrl;
	}

	public void incrementVisitCount() {
		this.visitCount++;
	}
}
