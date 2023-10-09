package com.programmers.urlshortener.domain.url.application;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.urlshortener.domain.encoder.domain.UrlEncoder;
import com.programmers.urlshortener.domain.url.application.dto.request.ShortUrlCreateRequest;
import com.programmers.urlshortener.domain.url.application.dto.response.ShortUrlResponse;
import com.programmers.urlshortener.domain.url.domain.Url;
import com.programmers.urlshortener.domain.url.domain.UrlRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class UrlService {

	private final UrlRepository urlRepository;
	private final Map<String, UrlEncoder> urlEncoders;

	public ShortUrlResponse createShortUrl(ShortUrlCreateRequest shortUrlCreateRequest) {
		Url url = ShortUrlCreateRequest.toUrl(shortUrlCreateRequest);
		Url savedUrl = urlRepository.save(url);

		String urlEncoderName = url.getEncoderType().toString();

		UrlEncoder urlEncoder = urlEncoders.get(urlEncoderName);
		String encodedShortUrl = urlEncoder.encode(savedUrl.getId());

		savedUrl.updateShortUrl(encodedShortUrl);

		return ShortUrlResponse.from(savedUrl);
	}
}
