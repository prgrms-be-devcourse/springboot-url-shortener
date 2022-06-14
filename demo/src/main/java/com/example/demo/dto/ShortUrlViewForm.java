package com.example.demo.dto;

import com.example.demo.shorturlutil.ShortenAlgorithm;
import lombok.Getter;

@Getter
public class ShortUrlViewForm {

    private String url;
    private ShortenAlgorithm shortenAlgorithm;
}
