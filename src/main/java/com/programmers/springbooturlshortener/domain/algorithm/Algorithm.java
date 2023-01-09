package com.programmers.springbooturlshortener.domain.algorithm;

public interface Algorithm {

	String encode(Long id);

	Long decode(String shortUrl);
}
