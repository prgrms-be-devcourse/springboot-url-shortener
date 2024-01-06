package org.daehwi.shorturl.util;

import static lombok.AccessLevel.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.daehwi.shorturl.controller.dto.ResponseStatus;
import org.daehwi.shorturl.exception.CustomException;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class UrlValidator {

	private static final String HTTP_SCHEME = "http";
	private static final String HTTPS_SCHEME = "https";
	private static final String PROTOCOL_SEPARATOR = "://";

	public static String validateUrl(String urlString) {
		try {
			validateProtocol(urlString);
			new URL(urlString);
			return getCleanURL(urlString);
		} catch (MalformedURLException e) {
			log.error("An error occurred while validating URL: ", e);
			throw new CustomException(ResponseStatus.INVALID_URL);
		}
	}

	private static void validateProtocol(String urlString) {
		int protocolEndIndex = urlString.indexOf(PROTOCOL_SEPARATOR);
		if (protocolEndIndex == -1) {
			throw new CustomException(ResponseStatus.PROTOCOL_NOT_FOUND);
		}
		String protocol = urlString.substring(0, protocolEndIndex).toLowerCase();
		if (!protocol.equals(HTTP_SCHEME) && !protocol.equals(HTTPS_SCHEME)) {
			throw new CustomException(ResponseStatus.PROTOCOL_NOT_SUPPORTED);
		}
	}

	private static String getCleanURL(String urlString) {
		urlString = urlString
			.replace(HTTP_SCHEME + PROTOCOL_SEPARATOR, "")
			.replace(HTTPS_SCHEME + PROTOCOL_SEPARATOR, "");
		log.info("Clean URL: {}", urlString);
		return urlString;
	}
}
