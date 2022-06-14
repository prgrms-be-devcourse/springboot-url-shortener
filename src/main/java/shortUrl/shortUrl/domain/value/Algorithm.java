package shortUrl.shortUrl.domain.value;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shortUrl.shortUrl.algorithm.Base56Util;
import shortUrl.shortUrl.algorithm.Sha256Util;

@RequiredArgsConstructor
@Slf4j
public enum Algorithm {

    BASE_56("A") {
        @Override
        public String encoding(Long value) {
            log.info("url {} encoding. original url => {}", BASE_56, value);
            return new Base56Util().encoding(value) + BASE_56.algorithmCode;
        }
    },
    SHA_256("B") {
        @Override
        public String encoding(Long value) {
            log.info("url {} encoding. original url => {}", SHA_256, value);
            return new Sha256Util().encoding(value) + SHA_256.algorithmCode;
        }
    };

    private final String algorithmCode;

    public abstract String encoding(Long value);
}
