package com.example.demo.service;

import com.example.demo.domain.Url;
import com.example.demo.repository.ShortUrlRepository;
import com.example.demo.shorturlutil.Base62Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    @Transactional
    public String createShortUrl(String url) {
        Optional<Url> findUrl = findByUrl(url);
        if (findUrl.isEmpty()) {
            Url originUrl = new Url(url, 1);
            Url savedUrl = shortUrlRepository.save(originUrl);
            log.info("id: " + savedUrl.getId());
            return Base62Util.encoding(savedUrl.getId());
        }
        return Base62Util.encoding(findUrl.get().getId());
    }

    private Optional<Url> findByUrl(String url) {
        return shortUrlRepository.findUrlByOriginUrl(url);
    }
}
