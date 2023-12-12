package marco.urlshortener.service;

import lombok.RequiredArgsConstructor;
import marco.urlshortener.domain.Url;
import marco.urlshortener.repositoy.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    public String getShortUrl(String longUrl) {
        Optional<Url> url = urlRepository.findByLongUrl(longUrl);

        if (url.isEmpty()) {
            // TODO: short url 만들기
        }

        return url.get()
                .getShortUrl();
    }
}
