package com.dev.urlshortner.dto;

import java.time.LocalDateTime;

import com.dev.urlshortner.entity.Url;

public record UrlStatsResponse(String originUrl, Long visitCount, LocalDateTime createdAt) {
	public static UrlStatsResponse of(Url url) {
		return new UrlStatsResponse(url.getOriginalUrl(), url.getVisitCount(), url.getCreatedAt());
	}
}
