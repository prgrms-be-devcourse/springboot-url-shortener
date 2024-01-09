package marco.urlshortener.service;

import lombok.RequiredArgsConstructor;
import marco.urlshortener.domain.Url;
import marco.urlshortener.dto.UrlRequest;
import marco.urlshortener.dto.UrlResponse;
import marco.urlshortener.repositoy.UrlRepository;
import marco.urlshortener.util.Base62;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    @Transactional
    public UrlResponse getShortUrl(UrlRequest request) {
        String longUrl = request.longUrl();

        Url url = urlRepository.findByLongUrl(longUrl)
                .orElseGet(getUrlSupplier(longUrl));

        String encoded = Base62.encode(url.getId());
        String fullPath = getFullPath(encoded);

        return new UrlResponse(fullPath);
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

    private String getFullPath(String encoded) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString() + '/' + encoded;
    }
}
