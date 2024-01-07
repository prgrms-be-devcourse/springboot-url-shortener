package com.pgms.shorturlcoredomain.util;

import com.pgms.shorturlcoredomain.url.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class Md5HashConverter implements AlgorithmConverter{

    private final UrlRepository urlRepository;

    @Override
    public String encode(Long v) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(v.toString().getBytes(StandardCharsets.UTF_8));

            StringBuffer hexMD5hash = new StringBuffer();
            for (byte b : md5.digest()) {
                String hexString = String.format("%02x", b);
                hexMD5hash.append(hexString);
            }

            int index = IntStream.range(6, hexMD5hash.length())
                    .takeWhile(i -> {
                        var curHash = hexMD5hash.substring(0, i);
                        return urlRepository.findUrlByShortUrl(curHash).isEmpty();
                    })
                    .findFirst()
                    .orElseGet(hexMD5hash::length);

            return hexMD5hash.substring(0, index);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
