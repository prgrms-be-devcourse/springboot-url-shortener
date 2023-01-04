package org.prgrms.url.shortner.springbooturlshortner.domain.service.converter;

import java.util.Map;

import org.prgrms.url.shortner.springbooturlshortner.domain.Url;
import org.prgrms.url.shortner.springbooturlshortner.domain.algorithm.ShortUrlAlgorithm;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateRequest;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.SearchResponse;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UrlConverter {

	private final Map<String, ShortUrlAlgorithm> algorithmMap;

	public Url getUrl(CreateRequest createRequest) {
		return Url.builder()
			.originUrl(createRequest.getOriginUrl())
			.shortUrlAlgorithm(algorithmMap.get(createRequest.getShortUrlAlgorithm()))
			.build();
	}

	public CreateResponse getCreateResponse(String shortenUrl) {
		return new CreateResponse(shortenUrl);
	}

	public SearchResponse getSearchResponse(String originUrl) {
		return new SearchResponse(originUrl);
	}

}
