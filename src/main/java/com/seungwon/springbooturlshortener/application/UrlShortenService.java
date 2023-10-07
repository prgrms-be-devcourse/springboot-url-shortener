package com.seungwon.springbooturlshortener.application;

import com.seungwon.springbooturlshortener.domain.Url;

public interface UrlShortenService {
	String shorten(Url original);
}
