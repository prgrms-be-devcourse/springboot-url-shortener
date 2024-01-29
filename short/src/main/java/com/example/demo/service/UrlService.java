package com.example.demo.service;

import com.example.demo.dto.UrlRequest;
import com.example.demo.dto.UrlResponse;
import com.example.demo.entity.Url;
import com.example.demo.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private static final char[] words = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private final UrlRepository urlRepository;

    @Value("${domain.prefix}")
    private String prefix;

    @Transactional
    public UrlResponse saveUrl(UrlRequest urlRequest) {
        String orgUrl = urlRequest.orgUrl();
        Url url = new Url(orgUrl);

        Url savedUrl = urlRepository.save(url);
        Long urlIndex = savedUrl.getId();
        String encoded = base62(urlIndex);

        savedUrl.setEncoded(encoded);

        String shortUrl = prefix + savedUrl.getEncoded();

        return UrlResponse.of(shortUrl, savedUrl.getOriginalUrl());
    }

    @Transactional(readOnly = true)
    public String redirectUrl(String encoded) {
        Url url = urlRepository.findByEncoded(encoded)
                .orElseThrow(() -> new RuntimeException("no such Url"));

        return url.getOriginalUrl();
    }

    private String base62(Long urlIndex) {
        StringBuilder result = new StringBuilder();

        while (urlIndex % 62 > 0 || result.equals("")) { // index가 62인 경우에도 적용되기 위해 do-while 형식이 되도록 구현
            result.append(words[urlIndex.intValue() % 62]);
            urlIndex = urlIndex / 62;
        }

        return result.toString();
    }
}
