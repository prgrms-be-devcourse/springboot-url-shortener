package com.example.demo.service;

import com.example.demo.domain.Url;
import com.example.demo.exception.ShortUrlNotFoundException;
import com.example.demo.repository.ShortUrlRepository;
import com.example.demo.shorturlutil.Base36Util;
import com.example.demo.shorturlutil.Base62Util;
import com.example.demo.shorturlutil.ShortenAlgorithm;
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

    private static final int FIRST_COUNT = 1;

    @Transactional
    public String createShortUrl(String url, ShortenAlgorithm shortenAlgorithm) {
        Optional<Url> findUrl = findByUrl(url);
        if (findUrl.isEmpty()) {
            Url originUrl = new Url(url, FIRST_COUNT);
            Url savedUrl = shortUrlRepository.save(originUrl);
            return encodeByShortenAlgorithm(shortenAlgorithm, savedUrl.getId());
        }
        findUrl.get().increaseCalledTimes();
        return encodeByShortenAlgorithm(shortenAlgorithm, findUrl.get().getId());
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


    private String encodeByShortenAlgorithm(ShortenAlgorithm shortenAlgorithm, int id) {
        if(shortenAlgorithm.equals(ShortenAlgorithm.BASE36)) {
            return Base36Util.encoding(id);
        }
        return Base62Util.encoding(id);
    }

}
