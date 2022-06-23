package com.ryu.urlshortner.url.application;

import com.ryu.urlshortner.common.encoder.UrlEncoder;
import com.ryu.urlshortner.common.exception.UrlExpiredException;
import com.ryu.urlshortner.common.exception.UrlNotFoundException;
import com.ryu.urlshortner.url.application.dto.request.UrlTransformDto;
import com.ryu.urlshortner.url.application.dto.response.UrlDto;
import com.ryu.urlshortner.url.domain.Url;
import com.ryu.urlshortner.url.domain.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlEncoder urlEncoder;

    public UrlService(UrlRepository urlRepository, UrlEncoder urlEncoder) {
        this.urlRepository = urlRepository;
        this.urlEncoder = urlEncoder;
    }

    @Transactional
    public UrlDto transform(UrlTransformDto urlTransformDto) {
        if (urlTransformDto == null) {
            throw new IllegalArgumentException();
        }

        Optional<Url> foundUrl = urlRepository.findByOriginUrl(urlTransformDto.getOriginUrl());

        if (foundUrl.isPresent()) {
            return UrlDto.from(foundUrl.get());
        }

        Url savedUrl = urlRepository.save(urlTransformDto.toEntity());
        String shortUrl = urlEncoder.encode(savedUrl.getSeq());
        savedUrl.setShortUrl(shortUrl);
        return UrlDto.from(savedUrl);
    }

    public String getOriginUrl(String shortUrl) {
        if (shortUrl == null) {
            throw new IllegalArgumentException();
        }

        long seq = urlEncoder.decode(shortUrl);
        Url url = urlRepository.findBySeq(seq).orElseThrow(UrlNotFoundException::new);

        if (url.getExpiredAt() != null && url.getExpiredAt().isBefore(LocalDateTime.now())) {
            urlRepository.delete(url);
            throw new UrlExpiredException();
        }

        url.increaseHits();
        return url.getOriginUrl();
    }
}
