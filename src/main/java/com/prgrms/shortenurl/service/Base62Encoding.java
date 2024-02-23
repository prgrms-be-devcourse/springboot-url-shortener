package com.prgrms.shortenurl.service;

public class Base62Encoding {

    private static final int BASE62 = 62;
    private static final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String encode(long param, int length) {
        StringBuffer sb = new StringBuffer();
        while (param > 0 || sb.length() < length) {
            sb.append(BASE62_CHAR.charAt((int) (param % BASE62)));
            param /= BASE62;
        }
        return sb.toString();
    }

    public static long decode(String param) {
        long sum = 0;
        long power = 1;
        for (int i = 0; i < param.length(); i++) {
            sum += BASE62_CHAR.indexOf(param.charAt(i)) * power;
            power *= BASE62;
        }
        return sum;
    }

}
