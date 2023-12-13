package marco.urlshortener.service;

import lombok.RequiredArgsConstructor;
import marco.urlshortener.domain.Url;
import marco.urlshortener.repositoy.UrlRepository;
import marco.urlshortener.util.Base62;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    @Transactional
    public String getShortUrl(String longUrl) {
        Url url = urlRepository.findByLongUrl(longUrl)
                .orElseGet(getUrlSupplier(longUrl));

        return Base62.encode(url.getId());
    }

    public String getLongUrl(String shortUrl) {
        long id = Base62.decode(shortUrl);

        return urlRepository.findById(id)
                .orElseThrow()
                .getLongUrl();
    }

    private Supplier<Url> getUrlSupplier(String longUrl) {
        return () -> urlRepository.save(new Url(longUrl));
    }
}
