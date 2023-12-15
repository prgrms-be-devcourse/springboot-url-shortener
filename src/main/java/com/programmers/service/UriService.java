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
        Optional<UriEntity> existingUriEntity = getExistingUriEntity(inputUri);

        if (existingUriEntity.isPresent()) {
            return uriConverter.convertCreateResponseFrom(existingUriEntity.get());
        }

        UriEntity newUriEntity = createAndSaveUriEntity(inputUri);

        ShortUriGenerateRequestDto requestDto = buildShortUriRequestDto(newUriEntity);
        String shortUri = uriGenerator.generate(requestDto);

        newUriEntity.initShortUri(shortUri);
        return uriConverter.convertCreateResponseFrom(newUriEntity);
    }

    public String getOriginalUri(String shortUri) {
        UriEntity findUriEntity = uriRepository.findByShortUriEquals(shortUri).orElseThrow();
        findUriEntity.increaseCount();
        return findUriEntity.getOriginalUri();
    }

    private Optional<UriEntity> getExistingUriEntity(String inputUri) {
        return uriRepository.findByOriginalUriEquals(inputUri);
    }

    private UriEntity createAndSaveUriEntity(String inputUri) {
        return uriRepository.save(new UriEntity(inputUri));
    }

    private ShortUriGenerateRequestDto buildShortUriRequestDto(UriEntity uriEntity) {
        return ShortUriGenerateRequestDto.builder()
                .originalUri(uriEntity.getOriginalUri())
                .index(uriEntity.getId())
                .build();
    }


}
