package com.programmers.springbooturlshortener.global.domain.converter;

public interface AlgorithmConverter {

	String encode(Long param);

	Long decode(String param);
}
