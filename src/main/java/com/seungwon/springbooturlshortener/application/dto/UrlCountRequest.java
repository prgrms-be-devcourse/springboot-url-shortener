package com.seungwon.springbooturlshortener.application.dto;

import org.hibernate.validator.constraints.URL;

import com.seungwon.springbooturlshortener.domain.Url;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record UrlCountRequest(
	@NonNull
	@NotBlank
	@URL(message = "유효한 URL이 아닙니다.")
	String originalUrl
) {
	public static Url from(UrlCountRequest urlCountRequest) {
		return new Url(urlCountRequest.originalUrl);
	}
}
