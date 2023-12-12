package com.programmers.service;

import com.programmers.component.IndexUriGenerator;
import com.programmers.component.ShortUriGenerator;
import com.programmers.component.UriConverter;
import com.programmers.dto.ShortUriGenerateRequestDto;
import com.programmers.entity.UriEntity;
import com.programmers.model.CreateResponse;
import com.programmers.repository.UriRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UriService {
    private final UriRepository uriRepository;
    private final ShortUriGenerator uriGenerator;
    private final UriConverter uriConverter;

    public UriService(UriRepository uriRepository, UriConverter uriConverter) {
        this.uriRepository = uriRepository;
        this.uriGenerator = new IndexUriGenerator();
        this.uriConverter = uriConverter;
    }

    public CreateResponse createShortUri(String inputUri) {
        Optional<UriEntity> optionalUriEntity = uriRepository.findByOriginalUriEquals(inputUri);
        if(optionalUriEntity.isPresent()) {
            UriEntity existUriEntity = optionalUriEntity.get();
            return uriConverter.convertCreateResponseFrom(existUriEntity);
        }
        UriEntity uriEntity = new UriEntity(inputUri);
        UriEntity saveUriEntity = uriRepository.save(uriEntity);

        ShortUriGenerateRequestDto requestDto = ShortUriGenerateRequestDto.builder()
                .originalUri(inputUri)
                .index(saveUriEntity.getId())
                .build();
        String shortUri = uriGenerator.generate(requestDto);
        saveUriEntity.initShortUri(shortUri);
        return uriConverter.convertCreateResponseFrom(saveUriEntity);
    }

    public String getOriginalUri(String shortUri) {
        UriEntity findUriEntity = uriRepository.findByShortUriEquals(shortUri).orElseThrow();
        findUriEntity.increaseCount();
        return findUriEntity.getOriginalUri();
    }
}
