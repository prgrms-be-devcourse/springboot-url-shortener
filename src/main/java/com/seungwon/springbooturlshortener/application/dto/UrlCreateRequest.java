package com.seungwon.springbooturlshortener.application.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.bind.DefaultValue;

import com.seungwon.springbooturlshortener.domain.Url;

import lombok.NonNull;

public record UrlCreateRequest(

	@NonNull
	@URL(message = "유효한 URL이 아닙니다.")
	String originalUrl,

	String strategy
) {
	public static Url from(UrlCreateRequest urlCreateRequest) {
		return new Url(urlCreateRequest.originalUrl);
	}
}
