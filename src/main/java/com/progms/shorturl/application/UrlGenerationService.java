package com.progms.shorturl.application;

import org.springframework.stereotype.Service;

import static com.progms.shorturl.util.ConstantURL.BASE62;
import static com.progms.shorturl.util.ConstantURL.BASE62_RAINBOW_TABLE;

@Service
public class UrlGenerationService {

    public String parseBase62(long id) {

        StringBuilder parseData = new StringBuilder();

        while (id > 0) {
            parseData.append(BASE62_RAINBOW_TABLE[(int) (id % BASE62)]);
            id /= BASE62;
        }

        return parseData.reverse().toString();
    }
}
