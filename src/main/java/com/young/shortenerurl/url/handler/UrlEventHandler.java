package com.young.shortenerurl.url.handler;

import com.young.shortenerurl.url.handler.dto.OriginUrlFindEvent;
import com.young.shortenerurl.url.infrastructures.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class UrlEventHandler {

    private final UrlRepository urlRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleFindOriginUrlEvent(OriginUrlFindEvent event) {
        urlRepository.increaseVisitCount(event.urlId());
    }
}
