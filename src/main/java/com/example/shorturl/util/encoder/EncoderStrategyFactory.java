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

    private final UrlRepository urlRepository;

    public EncoderStrategy createEncoderStrategy(Algorithm algorithm) {
        if (Objects.requireNonNull(algorithm) == Algorithm.BASE_62) {
            return new Base62();
        } else if (Objects.requireNonNull(algorithm) == Algorithm.SHA_256) {
            return new Sha256(urlRepository);
        }
        throw new CustomException(ErrorCode.ALGORITHM_NOT_SUPPORTED);
    }
}
