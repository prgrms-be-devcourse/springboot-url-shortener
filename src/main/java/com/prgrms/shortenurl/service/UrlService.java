package com.prgrms.shortenurl.service;

import com.google.common.hash.Hashing;
import com.prgrms.shortenurl.domain.Url;
import com.prgrms.shortenurl.domain.UrlRepository;
import com.prgrms.shortenurl.exception.UrlNotFoundException;
import lombok.NonNull;
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

    private Url save(@NonNull String originUrl) {
        return urlRepository.save(Url.builder()
                .originUrl(originUrl).build());
    }

    public static String shortenUrlByMurMur(@NonNull Url url) {
        return Hashing.murmur3_32_fixed().hashString(url.getOriginUrl(), Charset.defaultCharset()).toString();
    }

    public static String shortenUrlByBase62(@NonNull Url url) {
        int intKey = Math.toIntExact(url.getId());

        // 이진수 변환
        String binary = Converter.convertToBinary(intKey);

        // 16진수 변환
        String hex = Converter.convertToHex(Integer.parseInt(binary));

        return Base62Encoding.encode(HexFormat.fromHexDigitsToLong(hex));
    }

    @Transactional
    public Url addLink(@NonNull String originUrl, @NonNull String encodingType) {
        Url url = save(originUrl);
        String key = EnumConverter.convert(encodingType).encode(url);
        url.updateShortenUrl(key);
        return url;
    }

    @Transactional
    public String getUrlByKey(@NonNull String key) {
        Url url = urlRepository.findByShortenKey(key)
                .orElseThrow(UrlNotFoundException::new);
        url.updateCount();
        return url.getOriginUrl();
    }
}
