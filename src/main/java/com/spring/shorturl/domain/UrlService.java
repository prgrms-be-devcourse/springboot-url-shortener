package com.spring.shorturl.domain;

import com.spring.shorturl.domain.data.MyPageUrl;
import com.spring.shorturl.domain.data.Url;
import com.spring.shorturl.domain.data.UrlDto;
import com.spring.shorturl.domain.function.Base62Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class UrlService {
    private final UrlRepository urlRepository;
    private final Converter converter;
    private final MyPageUrl myPageUrl;

    public Long save(UrlDto.SaveRequest dto) {
        Url url;
        try{
            url = urlRepository.findByOriginUrl(dto.originUrl()).get();
        } catch (NoSuchElementException e){
            url = Url.builder()
                    .originUrl(dto.originUrl())
                    .build();

            Url saved = urlRepository.save(url);
            String encodedUrl = Base62Util.encoding(Math.toIntExact(saved.getId()));
            saved.changeShortUrl(myPageUrl.shortUrlWithMyPageName(encodedUrl));
            return saved.getId();
        }
        return url.getId();
    }

    public Url findByShortUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl).get();
        url.addRequstCount();
        return url;
    }

    @Transactional(readOnly = true)
    public Url findById(Long id){
        return urlRepository.findById(id).get();
    }
}
