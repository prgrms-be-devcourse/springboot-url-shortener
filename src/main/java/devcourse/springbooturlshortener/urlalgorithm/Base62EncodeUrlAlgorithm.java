package devcourse.springbooturlshortener.urlalgorithm;

import org.springframework.stereotype.Component;

@Component
public final class Base62EncodeUrlAlgorithm implements UrlAlgorithm {

    private static final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int BASE62_LENGTH = BASE62_CHAR.length();
    private static final Long MAX_VALUE = 62L * 62L * 62L * 62L * 62L * 62L * 62L * 62L - 1; // 218_340_105_584_895L

    @Override
    public String urlEncoder(Long id) {
        if (id > MAX_VALUE) {
            throw new IllegalArgumentException("최대값을 초과했습니다.");
        }
        return encoding(id);
    }

    @Override
    public Long urlDecoder(String encodeStr) {
        return decoding(encodeStr);
    }

    private String encoding(long param) {
        StringBuilder stringBuilder = new StringBuilder();

        while (param > 0) {
            stringBuilder.append(BASE62_CHAR.charAt((int) (param % BASE62_LENGTH)));
            param /= BASE62_LENGTH;
        }

        return stringBuilder.toString();
    }

    private Long decoding(String param) {
        long sum = 0;
        long power = 1;

        for (int i = 0; i < param.length(); i++) {
            sum += BASE62_CHAR.indexOf(param.charAt(i)) * power;
            power *= BASE62_LENGTH;
        }

        return sum;
    }
}
