package com.prgrms.urlshortener.controller;

public record CreateShortenUrlForm(
        String originUrl,
        String strategy
) {
}