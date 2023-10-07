package com.young.shortenerurl.url.application;

import com.young.shortenerurl.url.application.dto.UrlCreateRequest;
import com.young.shortenerurl.url.application.dto.UrlInfoFindResponse;
import com.young.shortenerurl.global.generator.UniqueKeyGenerator;
import com.young.shortenerurl.url.infrastructures.UrlRepository;
import com.young.shortenerurl.url.model.EncodedUrl;
import com.young.shortenerurl.url.model.Url;
import com.young.shortenerurl.url.util.Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UniqueKeyGenerator uniqueKeyGenerator;

    public UrlService(UrlRepository urlRepository, UniqueKeyGenerator uniqueKeyGenerator) {
        this.urlRepository = urlRepository;
        this.uniqueKeyGenerator = uniqueKeyGenerator;
    }

    @Transactional
    public String createUrl(UrlCreateRequest request) {
        Encoder encoder = request.encodingType().getEncoder();
        String encodedUrl = encoder.encode(uniqueKeyGenerator.generateKey());

        Url savedUrl = urlRepository.findByOriginUrl(request.originUrl()).orElseGet(() -> {
            Url url = new Url(
                    request.originUrl(),
                    new EncodedUrl(encodedUrl, request.encodingType())
            );

            return urlRepository.save(url);
        });

        return savedUrl.getEncodedUrl();
    }

    @Transactional
    public String findOriginUrl(String encodedUrl) {
        Url url = urlRepository.getByEncodedUrl(encodedUrl);

        url.increaseVisitCount();

        return url.getOriginUrl();
    }

    @Transactional(readOnly = true)
    public UrlInfoFindResponse findUrlInfo(String encodedUrl) {
        Url findUrl = urlRepository.getByEncodedUrl(encodedUrl);

        return UrlInfoFindResponse.from(findUrl);
    }

}
