package com.urlmaker.url;

import com.urlmaker.dto.UrlCreateRequestDTO;
import com.urlmaker.dto.UrlCreateResponseDTO;
import com.urlmaker.dto.UrlGetResponseDTO;
import com.urlmaker.algorithm.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final Algorithm algorithm;

    @Transactional
    public UrlCreateResponseDTO createShortenUrl(UrlCreateRequestDTO urlCreateRequestDTO) {
        Url url = urlCreateRequestDTO.toEntity();

        Optional<Url> retrievedURL = urlRepository.findByOriginUrl(url.getOriginUrl());

        if (retrievedURL.isPresent()) {
            Url savedUrl = retrievedURL.get();
            savedUrl.increaseRequestCount();

            return UrlCreateResponseDTO.builder()
                    .shortenUrl(algorithm.encode(savedUrl.getUrlId()))
                    .requestCount(savedUrl.getRequestCount())
                    .build();
        }

        Url savedUrl = urlRepository.save(url);
        String encodedShortenUrl = algorithm.encode(savedUrl.getUrlId());

        return UrlCreateResponseDTO.builder()
                .shortenUrl(encodedShortenUrl)
                .requestCount(url.getRequestCount())
                .build();
    }

    @Transactional(readOnly = true)
    public UrlGetResponseDTO getOriginUrl(String shortenUrl) {

        Long urlId = algorithm.decode(shortenUrl);

        Url originUrl = urlRepository.findById(urlId)
                .orElseThrow(() -> {
                    log.warn("no url");

                    throw new IllegalArgumentException("no url");
                });

        return UrlGetResponseDTO.builder()
                .originUrl(originUrl.getOriginUrl())
                .requestCount(originUrl.getRequestCount())
                .build();
    }
}
