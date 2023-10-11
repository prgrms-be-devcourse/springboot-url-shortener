package com.programmers.springbooturlshortener.global.domain.utils;

public class PrefixModifier {

	public static final String URL_PREFIX = "^(http://|https://)?(www\\.)?";
	public static final String HTTPS_PREFIX = "https://www.";

	public static String modifyUrlPrefix(String param) {
		return param.replaceAll(URL_PREFIX, "");
	}

	public static String removeBeforeLastSlash(String input) {
		int lastSlashIndex = input.lastIndexOf('/');

		if (lastSlashIndex != -1) {
			return input.substring(lastSlashIndex + 1);
		} else {
			return input;
		}
	}
}
