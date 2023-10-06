package com.young.shortenerurl.application;

import com.young.shortenerurl.application.dto.UrlCreateRequest;
import com.young.shortenerurl.application.dto.UrlVisitCountFindResponse;
import com.young.shortenerurl.infrastructures.UrlRepository;
import com.young.shortenerurl.model.Url;
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
                new Url(request.originUrl(), request.encodingType(), 0L)
        );

        url.encode();
        return url.getEncodedUrl();
    }

    @Transactional
    public String findOriginUrl(String encodedUrl) {
        Url url = urlRepository.getByEncodedUrl(encodedUrl);

        url.increaseVisitCount();

        return url.getOriginUrl();
    }

    @Transactional(readOnly = true)
    public UrlVisitCountFindResponse findUrlVisitCount(String encodedUrl) {
        Url findUrl = urlRepository.getByEncodedUrl(encodedUrl);

        return UrlVisitCountFindResponse.from(findUrl);
    }
}
