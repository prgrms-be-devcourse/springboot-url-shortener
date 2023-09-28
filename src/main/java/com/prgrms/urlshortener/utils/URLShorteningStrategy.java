package com.prgrms.urlshortener.utils;

public interface URLShorteningStrategy {
    String shortenURL(String originalURL);
}