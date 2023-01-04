package org.prgrms.url.shortner.springbooturlshortner.domain.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateRequest {

	@URL(message = "올바른 Url이 아닙니다.")
	private final String originUrl;

	@NotBlank(message = "단축 알고리즘은 Null일 수 없습니다.")
	private final String shortUrlAlgorithm;

	@Builder
	public CreateRequest(String originUrl, String shortUrlAlgorithm) {
		this.originUrl = originUrl;
		this.shortUrlAlgorithm = shortUrlAlgorithm;
	}
}
