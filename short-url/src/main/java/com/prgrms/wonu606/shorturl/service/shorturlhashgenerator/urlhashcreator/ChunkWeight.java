package com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.urlhashcreator;

public record ChunkWeight(int value) {

    public ChunkWeight {
        validate(value);
    }

    private void validate(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Weight 값은 양수여야 합니다. Current Weight: " + value);
        }
    }
}
