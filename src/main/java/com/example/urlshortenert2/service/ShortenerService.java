package com.example.urlshortenert2.service;

import com.example.urlshortenert2.ShorteningKeyMaker.ShorteningKeyMaker;
import com.example.urlshortenert2.dto.ShortenerRequestDto;
import com.example.urlshortenert2.dto.ShortenerResponseDto;
import com.example.urlshortenert2.model.ShortedUrl;
import com.example.urlshortenert2.repository.ShortedUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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
        Optional<ShortedUrl> foundShortedUrl = shortedUrlRepository.findShortedUrlByOriginUrl(dto.url());
        if (foundShortedUrl.isPresent()) {
            return new ShortenerResponseDto(foundShortedUrl.get().getShorteningKey());
        }

        String originUrl = dto.url();
        ShortedUrl shortedUrl = new ShortedUrl(originUrl);
        shortedUrlRepository.save(shortedUrl);
        String shorteningKey = shorteningKeyMaker.makeShorteningKey(shortedUrl.getId());
        shortedUrl.setShorteningKey(shorteningKey);
        return new ShortenerResponseDto(shorteningKey);
    }

    public String findByShorteningKey(String shorteningKey) {
        ShortedUrl shortedUrl = shortedUrlRepository.findShortedUrlByShorteningKey(shorteningKey)
                .orElseThrow(EntityNotFoundException::new);
        return shortedUrl.getOriginUrl();
    }

}
