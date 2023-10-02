package com.youngurl.shortenerurl.application;

import com.youngurl.shortenerurl.application.dto.UrlCreateRequest;
import com.youngurl.shortenerurl.infrastructures.UrlRepository;
import com.youngurl.shortenerurl.model.Url;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public String createUrl(UrlCreateRequest request) {
        Url url = urlRepository.save(
                new Url(request.originUrl(), request.encodingType())
        );

        url.encode();
        return url.getEncodedUrl();
    }

    public String findOriginUrl(String encodedUrl) {
        Url url = urlRepository.findByEncodedUrl(encodedUrl)
                .orElseThrow(() -> new EntityNotFoundException("해당 encodedUrl를 가진 url을 찾을 수 없습니다."));

        return url.getOriginUrl();
    }
}
