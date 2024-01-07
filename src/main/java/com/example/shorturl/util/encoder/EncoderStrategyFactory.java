package com.example.shorturl.util.encoder;

import com.example.shorturl.domain.Algorithm;
import com.example.shorturl.exception.CustomException;
import com.example.shorturl.exception.ErrorCode;
import com.example.shorturl.repository.UrlRepository;
import com.example.shorturl.util.algorithm.Base62;
import com.example.shorturl.util.algorithm.Sha256;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncoderStrategyFactory {

    private final Base62 base62;
    private final Sha256 sha256;

    public EncoderStrategy createEncoderStrategy(Algorithm algorithm) {
        if (algorithm == Algorithm.BASE_62) {
            return base62;
        } else if (algorithm == Algorithm.SHA_256) {
            return sha256;
        }
        throw new CustomException(ErrorCode.ALGORITHM_NOT_SUPPORTED);
    }
}
