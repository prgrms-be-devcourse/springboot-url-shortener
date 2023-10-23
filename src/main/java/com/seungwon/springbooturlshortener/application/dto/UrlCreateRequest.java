package com.seungwon.springbooturlshortener.application.dto;

import org.hibernate.validator.constraints.URL;

import com.seungwon.springbooturlshortener.domain.Url;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record UrlCreateRequest(
	@NonNull
	@NotBlank(message = "단축하고자 하는 URL을 입력하세요.")
	@URL(message = "유효하지 않은 url 입니다.")
	String originalUrl,

	String strategy
) {
	public static Url from(UrlCreateRequest urlCreateRequest) {
		return new Url(urlCreateRequest.originalUrl);
	}
}
