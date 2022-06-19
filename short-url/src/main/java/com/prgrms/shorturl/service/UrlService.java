package com.prgrms.shorturl.service;

import com.prgrms.shorturl.domain.Url;
import com.prgrms.shorturl.dto.UrlRequest;
import com.prgrms.shorturl.dto.UrlResponse;
import com.prgrms.shorturl.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UrlService {

    private static final char[] base62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlResponse convertUrl(UrlRequest urlRequest) {
        Long urlId = urlRepository.findByOriginalUrl(urlRequest.getUrl()).map(Url::getId)
            .orElseGet(() -> saveUrl(urlRequest));
        return new UrlResponse(encodeBase62(urlId));
    }

    public String searchByShortUrl(UrlRequest urlRequest) {
        return urlRepository.findById(decodeBase62(urlRequest.getUrl()))
            .orElseThrow(() -> new IllegalArgumentException("url is not exist")).getOriginalUrl();
    }

    private Long saveUrl(UrlRequest requestUrl) {
        Url savedUrl = urlRepository.save(new Url(requestUrl.getUrl()));
        return savedUrl.getId();
    }

    private Long decodeBase62(String shortenUrl) {
        long result = 0;
        int power = 1;
        for (int i = 0; i < shortenUrl.length(); i++) {
            int digit = new String(base62).indexOf(shortenUrl.charAt(i));
            result += (long) digit * power;
            power *= 62;
        }
        log.info("number -> {}", result);
        return result;
    }

    private String encodeBase62(Long idx) {
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < 7) {
            stringBuilder.append(base62[(int) (idx % 62)]);
            idx /= 62;
        }
        return stringBuilder.toString();
    }
}
