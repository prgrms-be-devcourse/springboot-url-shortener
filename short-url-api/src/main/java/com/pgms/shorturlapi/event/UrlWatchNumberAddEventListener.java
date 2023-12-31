package com.pgms.shorturlapi.event;

import com.pgms.shorturlcoredomain.exception.NoUrlException;
import com.pgms.shorturlcoredomain.url.Url;
import com.pgms.shorturlcoredomain.url.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class UrlWatchNumberAddEventListener {

    private final UrlRepository urlRepository;

    @Async
    @EventListener
    public void addUrlWatchNumber(UrlWatchNumberAddEvent event) {

        log.info(">>>>>> UrlWatchNumberAddEventListener");

        Url url = urlRepository.findUrlByShortUrl(event.shortUrl())
                .orElseThrow(NoUrlException::new);

        url.updateUrlWatchNumber();
    }
}
