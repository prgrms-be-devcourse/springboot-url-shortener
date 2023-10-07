package com.young.shortenerurl.url.application;

import com.young.shortenerurl.global.generator.UniqueKeyGenerator;
import com.young.shortenerurl.url.model.EncodingType;
import com.young.shortenerurl.url.util.Encoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EncodingExecutor {

    private final Map<String, Encoder> encoders;
    private final UniqueKeyGenerator uniqueKeyGenerator;

    public EncodingExecutor(UniqueKeyGenerator uniqueKeyGenerator) {
        this.encoders = Stream.of(EncodingType.values())
                .collect(Collectors.toMap(Enum::name, EncodingType::getEncoder));

        this.uniqueKeyGenerator = uniqueKeyGenerator;
    }

    public String encode(EncodingType encodingType){
        Assert.notNull(encodingType, "encodingType은 null이 들어오면 안됩니다.");

        Encoder encoder = encoders.get(encodingType.name());

        return encoder.encode(uniqueKeyGenerator.generateKey());
    }

}
