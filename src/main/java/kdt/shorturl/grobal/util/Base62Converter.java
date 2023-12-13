package kdt.shorturl.grobal.util;

public class Base62Converter implements ShortUrlConverter{
    private final String TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int RADIX = 62;

    public String encoding(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(TOKENS.charAt(num % RADIX));
            num /= RADIX;
        }
        return sb.toString();
    }
}
