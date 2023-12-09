package com.programmers.service;

import com.programmers.component.DefaultUriGenerator;
import com.programmers.component.ShortUriGenerator;
import com.programmers.dto.ShortUriGenerateRequestDto;
import com.programmers.entity.UriEntity;
import com.programmers.repository.UriRepository;
import org.springframework.stereotype.Service;

@Service
public class UriService {
    private final UriRepository uriRepository;
    private final ShortUriGenerator uriGenerator;

    public UriService(UriRepository uriRepository) {
        this.uriRepository = uriRepository;
        this.uriGenerator = new DefaultUriGenerator();
    }

    public String createShortUri(String inputUri) {
        UriEntity uriEntity = new UriEntity(inputUri);
        UriEntity saveUriEntity = uriRepository.save(uriEntity);

        ShortUriGenerateRequestDto requestDto = ShortUriGenerateRequestDto.builder()
                .originalUri(inputUri)
                .index(saveUriEntity.getId())
                .build();
        String shortUri = uriGenerator.generate(requestDto);
        saveUriEntity.initShortUri(shortUri);
        return saveUriEntity.getShortUri();
    }
}
