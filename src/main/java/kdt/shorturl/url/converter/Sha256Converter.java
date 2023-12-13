package kdt.shorturl.url.converter;

import kdt.shorturl.url.repository.UrlRepository;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@RequiredArgsConstructor
public class Sha256Converter implements ShortUrlConverter {
    private final UrlRepository urlRepository;
    private static final Random random = new Random();

    public String encoding(int num) {
        int maxAttempts = 3;
        int attempt = 0;

        while (attempt < maxAttempts) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] encodedHash = md.digest(String.valueOf(num).getBytes(StandardCharsets.UTF_8));

                String shortUrl = bytesToShortUrl(encodedHash, 8) + random.nextInt(10);

                if (!isShortUrlInUse(shortUrl)) {
                    return shortUrl;
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA-256로 인코딩 중 오류 발생", e);
            }
            attempt++;
        }
        throw new RuntimeException("여러 번 시도해도 고유한 짧은 URL을 생성하지 못했습니다");
    }

    private boolean isShortUrlInUse(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).isPresent();
    }

    private String bytesToShortUrl(byte[] bytes, int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < Math.min(bytes.length, length / 2); i++) {
            builder.append(String.format("%02x", bytes[i]));
        }
        return builder.toString().substring(0, length);
    }
}
