package com.example.urlshortenert2.service;

import com.example.urlshortenert2.ShorteningKeyMaker.ShorteningKeyMaker;
import com.example.urlshortenert2.dto.ShortenerRequestDto;
import com.example.urlshortenert2.dto.ShortenerResponseDto;
import com.example.urlshortenert2.model.ShortedUrl;
import com.example.urlshortenert2.repository.ShortedUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ShortenerService {

    private final ShortedUrlRepository shortedUrlRepository;
    private final ShorteningKeyMaker shorteningKeyMaker;

    public ShortenerService(ShortedUrlRepository shortedUrlRepository, ShorteningKeyMaker shorteningKeyMaker) {
        this.shortedUrlRepository = shortedUrlRepository;
        this.shorteningKeyMaker = shorteningKeyMaker;
    }

    @Transactional
    public ShortenerResponseDto createShortener(ShortenerRequestDto dto) {
        String originUrl = dto.url();
        ShortedUrl shortedUrl = new ShortedUrl(originUrl);
        shortedUrlRepository.save(shortedUrl);
        String shorteningKey = shorteningKeyMaker.makeShorteningKey(shortedUrl.getId());
        shortedUrl.setShorteningKey(shorteningKey);
        return new ShortenerResponseDto(shorteningKey);
    }

    public String findById(String randomId) {
        ShortedUrl shortedUrl = shortedUrlRepository.findShortedUrlByShorteningKey(randomId)
                .orElseThrow(EntityNotFoundException::new);
        return shortedUrl.getOriginUrl();
    }

}
