package marco.urlshortener.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import marco.urlshortener.domain.Url;
import marco.urlshortener.repositoy.UrlRepository;
import marco.urlshortener.util.Base62Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    @Transactional
    public String getShortUrl(String longUrl) {
        Optional<Url> url = urlRepository.findByLongUrl(longUrl);

        if (url.isEmpty()) {
            Url savedUrl = urlRepository.save(new Url(longUrl));
            Long id = savedUrl.getId();
            String encodedUrl = Base62Encoder.toBase62(id);

            savedUrl.setShortUrl(encodedUrl);

            return savedUrl.getShortUrl();
        }

        return url.get()
                .getShortUrl();
    }
}
