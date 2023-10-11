package com.programmers.springbooturlshortener.global.domain.converter;

import org.springframework.stereotype.Component;

@Component
public class Base62Converter implements AlgorithmConverter {

	private static final int BASE62_LENGTH = 62;
	private static final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	@Override
	public String encode(Long param) {
		StringBuilder sb = new StringBuilder();

		while(param > 0) {
			sb.append(BASE62_CHAR.charAt((int) (param % BASE62_LENGTH)));
			param /= BASE62_LENGTH;
		}

		return sb.toString();
	}

	@Override
	public Long decode(String param) {
		long sum = 0;
		long power = 1;

		for (int i = 0; i < param.length(); i++) {
			sum += BASE62_CHAR.indexOf(param.charAt(i)) * power;
			power *= BASE62_LENGTH;
		}

		return sum;
	}
}
