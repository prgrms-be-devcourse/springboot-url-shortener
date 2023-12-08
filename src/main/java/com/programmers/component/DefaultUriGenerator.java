package com.programmers.component;

import com.programmers.dto.ShortUriGenerateRequestDto;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class DefaultUriGenerator implements ShortUriGenerator {
    private final Random random = new Random();
    private static final int LENGTH = 7;
    private final char[] wordlist = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    @Override
    public String generate(ShortUriGenerateRequestDto requestDto) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(wordlist.length);
            sb.append(wordlist[randomIndex]);
        }
        return sb.toString();
    }
}
