package com.example.shorturl.util.encoder;

import com.example.shorturl.domain.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Encoder {

    private final EncoderStrategyFactory encoderStrategyFactory;

    public String encodeUrl(Long urlSequence, Algorithm algorithm) {
        EncoderStrategy encoderStrategy = encoderStrategyFactory.createEncoderStrategy(algorithm);
        return encoderStrategy.encodeUrl(urlSequence);
    }
}
