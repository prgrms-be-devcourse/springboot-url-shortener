package util;

import org.springframework.stereotype.Component;

public class Base62Converter {

    private static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String encode(Long v){
        StringBuilder sb = new StringBuilder();
        do {
            long i = v % 62;
            sb.append(BASE62[(int) i]);
            v /= 62;
        } while (v > 0);
        return sb.toString();
    }

    public static int decode(String v){
        int result = 0;
        int power = 1;

        for(int i = 0; i < v.length(); i++){
            int digit = new String(BASE62).indexOf(v.charAt(i));
            result += digit*power;
            power *= 62;
        }
        return result;
    }
}
