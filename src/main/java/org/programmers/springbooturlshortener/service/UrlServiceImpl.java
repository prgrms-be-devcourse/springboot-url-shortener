package org.programmers.springbooturlshortener.service;

import lombok.RequiredArgsConstructor;
import org.programmers.springbooturlshortener.Url;
import org.programmers.springbooturlshortener.encoding.Encoding;
import org.programmers.springbooturlshortener.repository.DuplicateUrlException;
import org.programmers.springbooturlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    @Override
    public String newUrl(String original, Encoding encoding) {
        String urlWithProtocol = UrlProtocolUtils.addingHttpIfNoProtocolIn(original);

        if (isDuplicate(urlWithProtocol)) {
            throw new DuplicateUrlException("중복 url 존재함");
        }
        Long key = urlRepository.save(new Url(urlWithProtocol)).getId();
        return encoding.encode(key);
    }

    private boolean isDuplicate(String original) {
        return urlRepository.existsByOriginal(original);
    }

    @Transactional
    @Override
    public String getOriginalUrl(String shortenUrl) {
        Long originalUrlKey = Long.valueOf(Encoding.getOriginalUrlKey(shortenUrl));
        Url savedUrl = urlRepository.findById(originalUrlKey).orElseThrow(() -> new NoUrlFoundException(shortenUrl, originalUrlKey));
        savedUrl.addCalledTime();

        return savedUrl.getOriginal();
    }

    @Override
    public long getCalledData(String shortenUrl) {
        Long originalUrlKey = Long.valueOf(Encoding.getOriginalUrlKey(shortenUrl));
        return urlRepository.findCalledById(originalUrlKey).getCalled();
    }
}