package com.example.demo.service;

import com.example.demo.dto.UrlRequestDTO;
import com.example.demo.dto.UrlResponseDTO;
import com.example.demo.entity.Url;
import com.example.demo.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private static final char[] words = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    @Transactional
    public UrlResponseDTO saveUrl(UrlRequestDTO urlRequestDTO) {
        String orgUrl = urlRequestDTO.orgUrl();
        Url url = new Url(orgUrl);

        Url savedUrl = urlRepository.save(url);
        Long urlIndex = savedUrl.getId();
        String shortUrl = base62(urlIndex);

        savedUrl.setEncoded(shortUrl);

        return UrlResponseDTO.of("https://dev.choi/" + savedUrl.getEncoded(), savedUrl.getUrl());
    }

    @Transactional(readOnly = true)
    public String redirectUrl(String encoded) {
        Url url = urlRepository.findByEncoded(encoded)
                .orElseThrow(() -> new RuntimeException("no such Url"));

        return url.getUrl();
    }

    private String base62(Long urlIndex) {
        String result = "";

        while (urlIndex % 62 > 0 || result.equals("")) { // index가 62인 경우에도 적용되기 위해 do-while 형식이 되도록 구현
            result = result + words[urlIndex.intValue() % 62];
            urlIndex = (Long) (urlIndex / 62);
        }

        return result;
    }
}
