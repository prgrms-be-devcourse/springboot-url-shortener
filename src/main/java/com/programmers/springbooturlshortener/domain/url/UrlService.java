package com.programmers.springbooturlshortener.domain.url;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.springbooturlshortener.domain.algorithm.Base62Algorithm;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.web.dto.UrlResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

	private final UrlRepository urlRepository;
	private final Base62Algorithm base62Algorithm;

	@Transactional
	public UrlResponseDto createShortUrl(UrlServiceRequestDto urlRequestDto) {

		Url url = urlRequestDto.toEntity();
		Optional<Url> optionalUrl = urlRepository.findByOriginUrl(url.getOriginUrl());

		if (optionalUrl.isPresent()) {
			Url savedUrl = optionalUrl.get();
			savedUrl.increaseRequestCount();

			return new UrlResponseDto(savedUrl.getOriginUrl(), savedUrl.getShortUrl(), savedUrl.getRequestCount());
		}

		Url savedUrl = urlRepository.save(url);
		String shortUrl = base62Algorithm.encode(savedUrl.getId());
		savedUrl.setShortUrl(shortUrl);
		return new UrlResponseDto(savedUrl.getOriginUrl(), shortUrl, savedUrl.getRequestCount());
	}

	@Transactional(readOnly = true)
	public UrlResponseDto getOriginUrl(String shortUrl) {

		Url url = urlRepository.findByShortUrl(shortUrl)
			.orElseThrow(() -> {
				throw new EntityNotFoundException();
			});

		return new UrlResponseDto(url.getOriginUrl(), url.getShortUrl(), url.getRequestCount());
	}
}

