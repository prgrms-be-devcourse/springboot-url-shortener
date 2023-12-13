package com.prgrms.shorturl.service;

import com.prgrms.shorturl.utils.*;
import com.prgrms.shorturl.domain.Url;
import com.prgrms.shorturl.dto.ShortUrlRequest;
import com.prgrms.shorturl.dto.ShortUrlResponse;
import com.prgrms.shorturl.exception.NoSuchOriginalUrlException;
import com.prgrms.shorturl.repository.ShortUrlRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.prgrms.shorturl.dto.ShortUrlResponse.*;
import static com.prgrms.shorturl.utils.UrlFactory.deleteProtocol;
import static com.prgrms.shorturl.utils.UrlFactory.extractProtocol;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    private Url save(String originalUrl) {
        HashIdFactory hashIdFactory = new HashIdFactory(HashAlgorithm.SHA_256);
        String hashId = hashIdFactory.generate(originalUrl);
        log.info("hashId: " + hashId);

        hashId = checkDuplication(hashId);
        log.info("completed hashId: " + hashId);

        EncodingFactory encodingFactory = new Base62EncodingFactory(hashId, originalUrl);
        return shortUrlRepository.save(encodingFactory.encode());
    }

    private String checkDuplication(String originalHash) {
        StringBuilder sb = new StringBuilder(originalHash.substring(0, 10));

        int index = 10;
        while(shortUrlRepository.findById(sb.toString()).isPresent()) {
            sb.append(originalHash.charAt(index++));
        }
        return sb.toString();
    }

    @Transactional
    public ShortUrlResponse getByOriginalUrl(@Valid ShortUrlRequest req) {
        String extractedProtocol = extractProtocol(req.originalUrl());
        String manufacturedUrl = deleteProtocol(req.originalUrl());
        Optional<Url> find = shortUrlRepository.findByOriginalUrl(manufacturedUrl);
        if(find.isEmpty()) {
            return toShortUrlResponse(save(manufacturedUrl), extractedProtocol);
        }

        Url url = find.get();
        url.countRequire();
        return toShortUrlResponse(url, extractedProtocol);
    }

    @Transactional(readOnly = true)
    public String getByShortUrl(@NotEmpty String shortUrl) {
        Url find = shortUrlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NoSuchOriginalUrlException("매칭되는 주소가 없습니다."));
        return find.getOriginalUrl();
    }
}
