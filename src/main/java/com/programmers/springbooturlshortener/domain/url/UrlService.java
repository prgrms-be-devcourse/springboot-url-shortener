package com.programmers.springbooturlshortener.domain.url;

import com.programmers.springbooturlshortener.domain.algorithm.Base62Algorithm;
import com.programmers.springbooturlshortener.domain.url.dto.UrlResponseDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

	private final UrlRepository urlRepository;
	private final Base62Algorithm base62Algorithm;

	@Transactional
	public UrlResponseDto createShortUrl(UrlServiceRequestDto urlRequestDto) {

		Optional<Url> optionalUrl = urlRepository.findByOriginUrl(urlRequestDto.originUrl());

		if (optionalUrl.isPresent()) {
			Url url = optionalUrl.get();
			optionalUrl.get().increaseRequestCount();

			return new UrlResponseDto(url.getOriginUrl(), url.getShortUrl(), url.getRequestCount());
		}

		Url url = urlRequestDto.toEntity();
		Url savedUrl = urlRepository.save(url);
		String shortUrl = base62Algorithm.encode(savedUrl.getId());
		savedUrl.setShortUrl(shortUrl);
		return new UrlResponseDto(savedUrl.getOriginUrl(), shortUrl, 1L);
	}

	@Transactional(readOnly = true)
	public UrlResponseDto getOriginUrl(String shortUrl) {

		Url url = urlRepository.findByShortUrl(shortUrl)
			.orElseThrow(() -> {
				throw new IllegalArgumentException();
			});

		return new UrlResponseDto(url.getOriginUrl(), url.getShortUrl(), url.getRequestCount());
	}
}

