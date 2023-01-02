package com.example.urlshortenert2.service;

import com.example.urlshortenert2.dto.ShortenerRequestDto;
import com.example.urlshortenert2.dto.ShortenerResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ShortenerService {



    public ShortenerResponseDto createShortener(ShortenerRequestDto dto) {
        return null;
    }

    public String findById(String randomId) {
        return null; // 진짜 url 반환
    }
}
