package com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.urlhashcreator;

import com.prgrms.wonu606.shorturl.domain.Url;
import com.prgrms.wonu606.shorturl.domain.UrlHash;
import org.springframework.stereotype.Component;

/**
 * 주어진 URL을 청크로 나눈 후, 각 청크를 기반으로 해시 문자를 생성합니다.
 * 이때, 각 청크의 ASCII 값의 합을 계산하여 알파벳 문자열에서 해당 인덱스의 문자를 찾아 해시를 형성합니다.
 * <p>
 * 이 방법은 URL의 길이와 상관 없이 일정한 길이의 해시를 생성할 수 있도록 합니다.
 */
@Component
public class ChunkBasedUrlHashCreator implements UrlHashCreator {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int HASH_SIZE = 8;

    @Override
    public UrlHash create(Url url, ChunkWeight weight) {
        String urlString = url.value();

        int chunkSize = Math.max(urlString.length() / HASH_SIZE, 1);
        StringBuilder shortUrlBuilder = new StringBuilder(HASH_SIZE);

        for (int i = 0; i < HASH_SIZE; i++) {
            String chunk = urlString.substring(i * chunkSize, Math.min((i + 1) * chunkSize, urlString.length()));
            shortUrlBuilder.append(getCharForChunk(chunk, weight));
        }

        return new UrlHash(shortUrlBuilder.toString());
    }

    private char getCharForChunk(String chunk, ChunkWeight weight) {
        int sumAscii = chunk.chars()
                .map(c -> c * weight.value())
                .sum();

        return ALPHABET.charAt(sumAscii % ALPHABET.length());
    }
}
