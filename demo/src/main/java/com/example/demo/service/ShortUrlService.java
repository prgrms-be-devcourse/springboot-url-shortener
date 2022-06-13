package com.example.demo.service;

import com.example.demo.domain.Url;
import com.example.demo.exception.ShortUrlNotFoundException;
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
            return Base62Util.encoding(savedUrl.getId());
        }
        return Base62Util.encoding(findUrl.get().getId());
    }

    public String getOriginUrl(String shortUrl) {
        int urlId = Base62Util.decoding(shortUrl);
        return shortUrlRepository.findById(urlId)
                 .orElseThrow(ShortUrlNotFoundException::new)
                 .getOriginUrl();
    }

    private Optional<Url> findByUrl(String url) {
        return shortUrlRepository.findUrlByOriginUrl(url);
    }
}
