package com.example.kdtspringbooturlshortener.urlinfo.application;

import com.example.kdtspringbooturlshortener.urlinfo.domain.UrlInfo;
import com.example.kdtspringbooturlshortener.urlinfo.domain.UrlInfoRepository;
import com.example.kdtspringbooturlshortener.urlinfo.request.UrlInfoReq;
import com.example.kdtspringbooturlshortener.urlinfo.response.UrlInfoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlInfoService {

    private final UrlInfoRepository urlInfoRepository;

    @Transactional
    public UrlInfoRes createShortUrl(UrlInfoReq urlInfoReq) {
        UrlInfo urlInfo = urlInfoRepository.save(urlInfoReq.toEntity());
        urlInfo.convertToShortUrl();

        return UrlInfoRes.toDto(urlInfo);
    }

    public UrlInfoRes getUrlInfo(String shortUrl) {
        UrlInfo urlInfo = urlInfoRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("해당 단축 URL에 해당하는 URL 정보 조회 실패"));

        return UrlInfoRes.toDto(urlInfo);
    }

    public String getOriginalUrl(String shortUrl) {
        UrlInfo urlInfo = urlInfoRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("해당 단축 URL에 해당하는 URL 정보 조회 실패"));

        return urlInfo.getOriginalUrl();
    }
}
