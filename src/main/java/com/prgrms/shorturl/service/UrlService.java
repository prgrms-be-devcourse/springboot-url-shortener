package com.prgrms.shorturl.service;

import com.prgrms.shorturl.utils.*;
import com.prgrms.shorturl.domain.Url;
import com.prgrms.shorturl.dto.UrlRequest;
import com.prgrms.shorturl.dto.UrlResponse;
import com.prgrms.shorturl.exception.NoSuchOriginalUrlException;
import com.prgrms.shorturl.repository.UrlRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.prgrms.shorturl.dto.UrlResponse.*;
import static com.prgrms.shorturl.utils.UrlFactory.deleteProtocol;
import static com.prgrms.shorturl.utils.UrlFactory.extractProtocol;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class UrlService {

    private final UrlRepository urlRepository;

    private Url save(String originalUrl, HashAlgorithm hashAlgorithm) {
        Url url = new Url(originalUrl, hashAlgorithm);
        Url save = urlRepository.save(url);
        log.info("url id: " + save.getId());

        url.hashing();
        log.info("hashId: " + url.getHash());

        url.changeHash(checkDuplication(url.getHash(), hashAlgorithm));
        log.info("completed hashId: " + url.getHash());

        url.encode();
        return urlRepository.save(url);
    }

    private String checkDuplication(String originalHash, HashAlgorithm hashAlgorithm) {
        if(hashAlgorithm == HashAlgorithm.SEQUENCE) {
            while(urlRepository.findByHash(originalHash).isPresent()) {
                originalHash += "0";
            }
            return originalHash;
        }

        StringBuilder sb = new StringBuilder(originalHash.substring(0, 10));

        int index = 10;
        while(urlRepository.findByHash(sb.toString()).isPresent()) {
            sb.append(originalHash.charAt(index++));
        }
        return sb.toString();
    }

    @Transactional(readOnly = true)
    public boolean isPresent(@Valid UrlRequest req) {
        String manufacturedUrl = deleteProtocol(req.originalUrl());
        return urlRepository.findByOriginalUrl(manufacturedUrl).isPresent();
    }

    @Transactional
    public UrlResponse getShortUrl(@Valid UrlRequest req) {
        String extractedProtocol = extractProtocol(req.originalUrl());
        String manufacturedUrl = deleteProtocol(req.originalUrl());
        Optional<Url> find = urlRepository.findByOriginalUrl(manufacturedUrl);
        if(find.isEmpty()) {
            return toShortUrlResponse(save(manufacturedUrl, req.hashAlgorithm()), extractedProtocol);
        }

        Url url = find.get();
        url.countRequire();
        return toShortUrlResponse(url, extractedProtocol);
    }

    @Transactional(readOnly = true)
    public String getOriginalUrl(@NotEmpty String shortUrl) {
        Url find = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NoSuchOriginalUrlException("매칭되는 주소가 없습니다."));
        return find.getOriginalUrl();
    }
}
