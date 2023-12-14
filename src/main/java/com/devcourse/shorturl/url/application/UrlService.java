package com.devcourse.shorturl.url.application;

import com.devcourse.shorturl.common.exception.CustomException;
import com.devcourse.shorturl.common.util.ShortUrlEncoder;
import com.devcourse.shorturl.url.domain.Url;
import com.devcourse.shorturl.url.dto.CreateUrlResponse;
import com.devcourse.shorturl.url.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.devcourse.shorturl.common.exception.ErrorCode.NOT_EXIST_SHORT_URL;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final ShortUrlEncoder urlEncoder;

    @Transactional
    public CreateUrlResponse createShortUrl(String longUrl) {
        Optional<Url> findUrl = urlRepository.findByLongUrl(longUrl);
        if (findUrl.isPresent()) {
            return new CreateUrlResponse(findUrl.get().getShortUrl(),
                findUrl.get().getHits()); // 단축 URL, 누적 요청횟수 리턴
        }

        Url url = new Url(longUrl, "", 0);
        Url savedUrl = urlRepository.save(url);
        String shortUrl = urlEncoder.encode(savedUrl.getId());
        savedUrl.insertShortUrl(shortUrl);

        return new CreateUrlResponse(shortUrl, 0);
    }

    @Transactional
    public String redirectLongUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
            .orElseThrow(() -> new CustomException(NOT_EXIST_SHORT_URL));
        url.addHits();
        return url.getLongUrl();
    }
}
