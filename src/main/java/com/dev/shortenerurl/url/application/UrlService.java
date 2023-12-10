package com.dev.shortenerurl.url.application;

import org.springframework.stereotype.Service;

import com.dev.shortenerurl.common.exception.CommonException;
import com.dev.shortenerurl.url.domain.IdGenerator;
import com.dev.shortenerurl.url.domain.UrlRepository;
import com.dev.shortenerurl.url.domain.model.Url;
import com.dev.shortenerurl.url.domain.model.query.OriginUrlModel;
import com.dev.shortenerurl.url.dto.UrlMapper;
import com.dev.shortenerurl.url.dto.response.OriginUrlInfo;
import com.dev.shortenerurl.url.dto.response.ShortenUrlInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

	private final UrlRepository urlRepository;
	private final IdGenerator idGenerator;

	public ShortenUrlInfo createShortenUrl(String originUrl, String algorithm) {
		Url urlEntity = urlRepository.findByOriginUrl(originUrl)
			.orElseGet(() -> {
				Url url = new Url(originUrl, idGenerator.get(), algorithm);
				urlRepository.save(url);
				return url;
			});

		return UrlMapper.toEncodedUrlInfo(urlEntity);
	}

	public OriginUrlInfo getOriginUrl(String encodedUrl) {
		OriginUrlModel originUrlModel = urlRepository.findOriginUrlByEncodedUrl(encodedUrl)
			.orElseThrow(() -> new CommonException("shortenUrl 에 해당되는 url 이 없습니다"));

		return UrlMapper.toOriginUrlInfo(originUrlModel);
	}
}
