package com.prgrms.shortenurl.service;

import com.google.common.hash.Hashing;
import com.prgrms.shortenurl.domain.Url;
import com.prgrms.shortenurl.domain.UrlRepository;
import com.prgrms.shortenurl.exception.UrlNotFoundException;
import lombok.NonNull;
import org.h2.value.ValueVarbinary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.HexFormat;

@Service
public class UrlService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UrlRepository urlRepository;
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private String shortenUrlByMurMur(@NonNull String originUrl) {
        return Hashing.murmur3_32_fixed().hashString(originUrl, Charset.defaultCharset()).toString();
    }

    private String shortenUrlByBase62(@NonNull Long id) {
        int intKey = Math.toIntExact(id);

        // 이진수 변환
        String binary = Converter.convertToBinary(intKey);

        // 16진수 변환
        String hex = Converter.convertToHex(Integer.parseInt(binary));

        return Base62.encode(HexFormat.fromHexDigitsToLong(hex));
    }

    private Url save(@NonNull String originUrl) {
        return urlRepository.save(Url.builder()
                .originUrl(originUrl).build());
    }

    @Transactional
    public Url addLink(@NonNull String originUrl) {
        Url url = save(originUrl);
        String key = shortenUrlByBase62(url.getId());
        url.updateShortenUrl(key);
        return url;
    }

    @Transactional(readOnly = true)
    public String getUrlByKey(@NonNull String key) {
        Url url = urlRepository.findByShortenKey(key)
                .orElseThrow(UrlNotFoundException::new);
        return url.getOriginUrl();
    }
}
