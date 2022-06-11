package com.kdt.shortener.service;

import com.kdt.shortener.domain.UrlInfo;
import com.kdt.shortener.repository.UrlInfoRepository;
import com.kdt.shortener.utils.Base62Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UrlInfoService {

    private final UrlInfoRepository repository;
    private final Base62Utils base62Utils;

    public UrlInfo turnOriginUrl(String shortUrl) {
        var arr = shortUrl.split("/");
        String hash = arr[arr.length - 1];
        Long id = base62Utils.decoding(hash);
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public String makeShortUrl(String originUrl) {
        var findOne = repository.findByOriginUrl(originUrl);
        if (findOne.isPresent()) {
            return base62Utils.encoding(findOne.get().getId());
        }
        UrlInfo urlInfo = new UrlInfo(originUrl);
        repository.save(urlInfo);
        return "http://localhost:8080/" + base62Utils.encoding(urlInfo.getId());
    }
}
