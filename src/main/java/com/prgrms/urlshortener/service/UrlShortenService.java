package com.prgrms.urlshortener.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.urlshortener.domain.dto.ShortenUrlRequest;
import com.prgrms.urlshortener.domain.dto.ShortenUrlResponse;
import com.prgrms.urlshortener.domain.entity.UrlInfo;
import com.prgrms.urlshortener.domain.repository.UrlRepository;
import com.prgrms.urlshortener.utils.Base62Util;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UrlShortenService {

	private final UrlRepository urlRepository;

	@Transactional
	public ShortenUrlResponse generateShortUrl(ShortenUrlRequest request) {
		UrlInfo urlInfo = request.toEntity();
		UrlInfo savedUrlInfo = urlRepository.save(urlInfo);
		String shortUrl = Base62Util.encode(savedUrlInfo.getId());

		return new ShortenUrlResponse(urlInfo.getOriginalUrl(), shortUrl);
	}

	public String getOriginalUrl(String shortUrl) {
		Long id = Base62Util.decode(shortUrl);
		UrlInfo savedUrlInfo = urlRepository.findById(id).orElseThrow(
			() -> new NoSuchElementException("URL not found on DB")
		);

		return savedUrlInfo.getOriginalUrl();
	}
}
