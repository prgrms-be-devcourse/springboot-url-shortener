package com.blessing333.urlshortner.domain.model.url.shortener.impl;

import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.shortener.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UrlShortenerProvider implements UrlShorteningManager {
    private final ApplicationContext context = new AnnotationConfigApplicationContext(UrlShortenerConfig.class);
    private final List<UrlShortener> urlShortenerList = new ArrayList<>();

    @Override
    public ShortUrl shorteningUrl(UrlCreateCommand urlCommand) {
        Class<? extends UrlCreateCommand> urlCreateCommandType = urlCommand.getClass();
        UrlShortener shortener = findSuitableShortener(urlCreateCommandType);
        try {
            return shortener.shorteningUrl(urlCommand);
        } catch (RuntimeException e) {
            throw new UrlShorteningFailException(e);
        }
    }

    private UrlShortener findSuitableShortener(Class<? extends UrlCreateCommand> urlCreateCommandType) {
        for (UrlShortener shortener : urlShortenerList) {
            if (shortener.supports(urlCreateCommandType)) {
                return shortener;
            }
        }
        throw new UrlShortenerNotFoundException("can't find suitable url shortener");
    }

    @PostConstruct
    private void initUrlShortenerList() {
        Map<String, UrlShortener> shortenerMap = context.getBeansOfType(UrlShortener.class);
        for (var shortenerEntry : shortenerMap.entrySet()) {
            urlShortenerList.add(shortenerEntry.getValue());
        }
    }
}
