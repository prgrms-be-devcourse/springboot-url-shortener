package com.programmers.springbooturlshortener.domain.url.util;

import java.net.URL;
import java.net.URLConnection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlValidation implements ConstraintValidator<UrlValid, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			if (hasNotProtocol(value)) {
				value = "https://" + value;
			}

			URL url = new URL(value);
			URLConnection conn = url.openConnection();
			conn.connect();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean hasNotProtocol(String originUrl) {
		return !(originUrl.startsWith("https://") || originUrl.startsWith("http://"));
	}
}
