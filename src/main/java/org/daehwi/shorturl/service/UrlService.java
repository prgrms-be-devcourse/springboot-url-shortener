package org.daehwi.shorturl.service;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

import org.daehwi.shorturl.controller.dto.EncodeType;
import org.daehwi.shorturl.controller.dto.ResponseStatus;
import org.daehwi.shorturl.controller.dto.ShortUrlRequest;
import org.daehwi.shorturl.domain.ShortUrl;
import org.daehwi.shorturl.exception.CustomException;
import org.daehwi.shorturl.repository.UrlRepository;
import org.daehwi.shorturl.util.UrlValidator;
import org.daehwi.shorturl.util.encoder.Base32Encoder;
import org.daehwi.shorturl.util.encoder.Base62Encoder;
import org.daehwi.shorturl.util.encoder.Encoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {

	private static final int DEFAULT_HASH_SIZE = 8;

	private final UrlRepository urlRepository;

	private final Map<EncodeType, Encoder> encoderMap = Map.of(
		EncodeType.BASE32, new Base32Encoder(),
		EncodeType.BASE62, new Base62Encoder()
	);

	public String createShortUrl(ShortUrlRequest requestDto) {
		final EncodeType encodeType = requestDto.getEncodeType();
		final Encoder encoder = encoderMap.get(encodeType);

		final String originUrl = UrlValidator.validateUrl(requestDto.getUrl());
		final BigInteger id = getUniqueId(originUrl, encodeType.name());
		String shortUrl = encoder.encode(id);
		urlRepository.save(new ShortUrl(id.longValue(), originUrl, shortUrl));
		return shortUrl;
	}

	public String getOriginUrl(String shortUrl) {
		final ShortUrl urlEntity = urlRepository.findByShortUrl(shortUrl)
			.orElseThrow(() -> new CustomException(ResponseStatus.SHORT_URL_NOT_FOUND));
		urlEntity.increaseRequestCount();
		return urlEntity.getOriginUrl();
	}

	private BigInteger getUniqueId(String originUrl, String encodeType) {
		int offset = 0;
		while (true) {
			final BigInteger id = Encoder.generateSha256(originUrl + encodeType, DEFAULT_HASH_SIZE + offset);
			final Optional<ShortUrl> shortUrl = urlRepository.findById(id.longValue());
			if (shortUrl.isEmpty() || shortUrl.get().getOriginUrl().equals(originUrl)) {
				return id;
			}
			++offset;
		}
	}
}
