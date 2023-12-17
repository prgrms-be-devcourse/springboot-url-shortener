package devcourse.springbooturlshortener.urlalgorithm;

public interface UrlAlgorithm {

    String urlEncoder(Long id);

    Long urlDecoder(String encodeStr);
}
