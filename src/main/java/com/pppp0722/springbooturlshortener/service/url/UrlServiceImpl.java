package com.pppp0722.springbooturlshortener.service.url;

import com.pppp0722.springbooturlshortener.domain.url.Url;
import com.pppp0722.springbooturlshortener.domain.url.UrlRepository;
import com.pppp0722.springbooturlshortener.domain.url.UrlRequestDto;
import com.pppp0722.springbooturlshortener.domain.url.UrlResponseDto;
import com.pppp0722.springbooturlshortener.util.Base62;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {

    @Value("${url}")
    private String URL;

    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public UrlResponseDto createUrl(UrlRequestDto urlRequestDto) {
        String originalUrl = urlRequestDto.getOriginalUrl();
        Optional<Url> retrievedUrl = urlRepository.findByOriginalUrl(originalUrl);
        Url url;
        if (retrievedUrl.isEmpty()) {
            url = urlRepository.save(urlRequestDto.toEntity());
        } else {
            url = retrievedUrl.get();
        }

        String shortUrl = URL + Base62.encoding(url.getId());

        return new UrlResponseDto(originalUrl, shortUrl);
    }

    @Override
    public String getOriginalUrl(String shortId) {
        long id = Base62.decoding(shortId);
        Optional<Url> retrievedUrl = urlRepository.findById(id);
        String originalUrl;
        if (retrievedUrl.isEmpty()) {
            throw new RuntimeException("url not found!");
        } else {
            originalUrl = retrievedUrl.get().getOriginalUrl();
        }
        return originalUrl;
    }
}
