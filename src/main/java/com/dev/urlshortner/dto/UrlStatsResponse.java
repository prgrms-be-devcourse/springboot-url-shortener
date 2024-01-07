package com.dev.urlshortner.dto;

import java.time.LocalDateTime;

import com.dev.urlshortner.domain.Url;

public record UrlStatsResponse(String originUrl, Long visitCount, LocalDateTime createdAt) {
	public static UrlStatsResponse from(Url url) {
		return new UrlStatsResponse(url.getOriginalUrl(), url.getVisitCount(), url.getCreatedAt());
	}
}
