package org.prgrms.urlshortener.application;

import org.prgrms.urlshortener.dto.request.EncodedUrlCreateRequest;
import org.prgrms.urlshortener.util.encoder.EncodePolicy;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Encoder {

	private final EncodePolicyFactory encodePolicyFactory;

	public String encode(EncodedUrlCreateRequest request) {
		EncodePolicy encodePolicy = encodePolicyFactory.createEncodePolicy(request.algorithm());
		String shortUrl = encodePolicy.encode(request.originUrl());

		return shortUrl;
	}

}
