package shortUrl.shortUrl.domain.value;

import lombok.RequiredArgsConstructor;
import shortUrl.shortUrl.algorithm.Base56Util;
import shortUrl.shortUrl.algorithm.Sha256Util;

@RequiredArgsConstructor
public enum Algorithm {

    BASE_56("A") {
        @Override
        public String encoding(Long value) {
            return new Base56Util().encoding(value) + BASE_56.algorithmCode;
        }

    },
    SHA_256("B") {
        @Override
        public String encoding(Long value) {
            return new Sha256Util().encoding(value) + SHA_256.algorithmCode;
        }
    };

    private final String algorithmCode;

    public abstract String encoding(Long value);
}
