package prgms.marco.springbooturlshortener.entity;

import org.springframework.stereotype.Component;

@Component
class UrlEncoder {
    
    private final int BASE62 = 62;
    private final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    String encoding(Long param) {
        StringBuffer sb = new StringBuffer();
        while (param > 0) {
            sb.append(BASE62_CHAR.charAt((int) (param % BASE62)));
            param /= BASE62;
        }
        return sb.toString();
    }
}
