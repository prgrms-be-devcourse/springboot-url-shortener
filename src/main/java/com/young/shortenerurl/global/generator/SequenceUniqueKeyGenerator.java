package com.young.shortenerurl.global.generator;

import org.springframework.stereotype.Component;

@Component
public class SequenceUniqueKeyGenerator implements UniqueKeyGenerator {

    private final SequenceRepository sequenceRepository;

    public SequenceUniqueKeyGenerator(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @Override
    public Long generateKey() {
        return sequenceRepository.save(new Sequence())
                .getId();
    }

}
