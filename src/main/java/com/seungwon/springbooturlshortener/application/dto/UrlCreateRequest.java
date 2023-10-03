package com.seungwon.springbooturlshortener.application.dto;

import com.seungwon.springbooturlshortener.domain.Url;

import lombok.NonNull;

public record UrlCreateRequest (

	@NonNull
	String originalUrl
){
	public static Url from(UrlCreateRequest urlCreateRequest) {
		return new Url(urlCreateRequest.originalUrl);
	}
}
