package com.prgrms.shorturl.domain.url.service;

import com.prgrms.shorturl.algorithm.Algorithm;
import com.prgrms.shorturl.algorithm.Base62;
import com.prgrms.shorturl.domain.url.dto.request.UrlCreateRequestDTO;
import com.prgrms.shorturl.domain.url.dto.response.UrlResponseDTO;
import com.prgrms.shorturl.domain.url.entity.Url;
import com.prgrms.shorturl.domain.url.repository.UrlRepository;
import com.prgrms.shorturl.common.exception.BaseException;
import com.prgrms.shorturl.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private final Algorithm algorithm = new Base62();

    @Transactional
    public UrlResponseDTO createShortUrl(UrlCreateRequestDTO urlCreateRequestDTO) {
        Url url = urlCreateRequestDTO.toEntity();

        Optional<Url> originUrl = urlRepository.findByOriginUrl(url.getOriginUrl());

        if (originUrl.isPresent()) {
            Url savedUrl = originUrl.get();
            savedUrl.increaseRequestCount();

            return new UrlResponseDTO(savedUrl.getOriginUrl(), savedUrl.getShortUrl(), savedUrl.getRequestCount());
        }

        Url savedUrl = urlRepository.save(url);

        String shortUrl = algorithm.encode(savedUrl.getId());

        savedUrl.setShortUrl(shortUrl);

        return new UrlResponseDTO(savedUrl.getOriginUrl(), savedUrl.getShortUrl(), savedUrl.getRequestCount());
    }

    @Transactional(readOnly = true)
    public UrlResponseDTO getOriginUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> {
                            log.warn("Url is not found");
                            throw new BaseException(ErrorCode.NOT_FOUND_URL);
                        }
                );

        return new UrlResponseDTO(url.getOriginUrl(), url.getShortUrl(), url.getRequestCount());
    }
}
