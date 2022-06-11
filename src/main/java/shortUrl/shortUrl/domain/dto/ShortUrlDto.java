package shortUrl.shortUrl.domain.dto;

import lombok.*;
import shortUrl.shortUrl.domain.value.Algorithm;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ShortUrlDto {

    private String shortUrl;
    private String originalUrl;
    private Long hits;
    private Algorithm algorithm;
}
