package com.blessing333.urlshortner.domain.model.url.key;

import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class Base62KeyGenerator implements KeyGenerator {
    private static final char[] codes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final long MAXIMUM_RANGE = 3521614606207L;

    private String encoding(long num) {
        if (num > MAXIMUM_RANGE)
            throw new KeyGenerateFailException("out of range for encoding value : " + num);
        Stack<Long> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        while (num >= 62) {
            stack.add(num % 62);
            num /= 62;
        }
        sb.append(codes[(int) num]);
        while (!stack.empty()) {
            Long pop = stack.pop();
            sb.append(codes[pop.intValue()]);
        }
        return sb.toString();
    }

    @Override
    public String generateKey(Long source) {
        return encoding(source);
    }
}
