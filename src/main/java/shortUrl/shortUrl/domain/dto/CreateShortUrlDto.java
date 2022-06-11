package shortUrl.shortUrl.domain.dto;

import lombok.*;
import shortUrl.shortUrl.domain.value.Algorithm;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class CreateShortUrlDto {

    private String originalUrl;

    private Algorithm algorithm;

}
