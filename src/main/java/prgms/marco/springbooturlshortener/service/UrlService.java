package prgms.marco.springbooturlshortener.service;

import static prgms.marco.springbooturlshortener.exception.Message.INVALID_SHORT_URL_EXP_MSG;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgms.marco.springbooturlshortener.entity.Url;
import prgms.marco.springbooturlshortener.exception.InvalidShortUrlException;
import prgms.marco.springbooturlshortener.repository.UrlRepository;

@Service
@Transactional(readOnly = true)
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public String createShortUrl(String originUrl) {

        Optional<Url> url = urlRepository.findByOriginUrl(originUrl);
        if (url.isPresent()) {
            return url.get().getShortUrl();
        }

        Url savedUrl = urlRepository.save(Url.createUrl(originUrl));
        savedUrl.convertIdToShortUrl(savedUrl.getId());

        return savedUrl.getShortUrl();
    }

    @Transactional
    public String findOriginUrlByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl)
            .map(url -> {
                url.addRequestCount();
                return url.getOriginUrl();
            })
            .orElseThrow(() -> new InvalidShortUrlException(INVALID_SHORT_URL_EXP_MSG));
    }
}
