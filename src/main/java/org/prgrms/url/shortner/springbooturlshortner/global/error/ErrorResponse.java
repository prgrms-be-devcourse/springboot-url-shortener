package org.prgrms.url.shortner.springbooturlshortner.global.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

	private int statusCode;
	private String message;

	@Builder
	public ErrorResponse(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
