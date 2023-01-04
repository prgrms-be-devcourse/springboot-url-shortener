package org.prgrms.url.shortner.springbooturlshortner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CreateResponse {

	private String prefix = "localhost:8080/geonwoo/";
	private String shortenUrl;

	public CreateResponse(String shortenUrl) {
		this.shortenUrl = shortenUrl;
	}
}
