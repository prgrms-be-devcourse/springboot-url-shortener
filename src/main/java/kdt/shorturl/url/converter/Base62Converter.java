package kdt.shorturl.url.converter;

import kdt.shorturl.grobal.exception.ShortUrlException;

public class Base62Converter implements ShortUrlConverter{
    private final String TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int RADIX = 62;

    public String encoding(int num) {
        if (num <= 0) {
            throw new ShortUrlException("0과 음수는 인코딩할 수 없습니다.");
        }
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(TOKENS.charAt(num % RADIX));
            num /= RADIX;
        }
        return sb.toString();
    }
}
