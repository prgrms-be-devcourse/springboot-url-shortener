package kdt.shorturl.grobal.util;

public class Base62Converter {
    private static final String TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int RADIX = 62;

    public static String encoding(long param) {
        StringBuilder sb = new StringBuilder();
        while(param > 0) {
            sb.append(TOKENS.charAt((int) (param % RADIX)));
            param /= RADIX;
        }
        return sb.toString();
    }
}
