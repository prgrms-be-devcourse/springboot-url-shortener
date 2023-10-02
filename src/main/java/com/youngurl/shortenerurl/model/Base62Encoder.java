package com.youngurl.shortenerurl.model;

import com.youngurl.shortenerurl.model.Encoder;
import org.springframework.stereotype.Component;

@Component
public class Base62Encoder implements Encoder {
    private static final int BASE62 = 62;
    private static final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Override
    public String encode(long index) {
        StringBuffer sb = new StringBuffer();
        while(index > 0) {
            sb.append(BASE62_CHAR.charAt((int) (index % BASE62)));
            index /= BASE62;
        }
        return sb.toString();
    }

    @Override
    public long decode(String param) {
        long sum = 0;
        long power = 1;
        for (int i = 0; i < param.length(); i++) {
            sum += BASE62_CHAR.indexOf(param.charAt(i)) * power;
            power *= BASE62;
        }
        return sum;
    }

}
